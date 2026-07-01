package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.dto.request.LeaveRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveRequestResponse;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.entity.LeaveType;
import com.myprojects.leavemanagementsystem.enums.Status;
import com.myprojects.leavemanagementsystem.exception.InvalidLeaveRequestException;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.exception.UnauthorizedActionException;
import com.myprojects.leavemanagementsystem.mapper.LeaveRequestMapper;
import com.myprojects.leavemanagementsystem.repository.EmployeeRepository;
import com.myprojects.leavemanagementsystem.repository.HolidayRepository;
import com.myprojects.leavemanagementsystem.repository.LeaveRequestRepository;
import com.myprojects.leavemanagementsystem.repository.LeaveTypeRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.AuditService;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveBalanceService;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveRequestService;
import com.myprojects.leavemanagementsystem.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final HolidayRepository holidayRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final NotificationService notificationService;
    private final AuditService auditService;
    private final LeaveRequestMapper leaveRequestMapper;

    @Override
    @Transactional
    public LeaveRequestResponse applyLeave(Integer employeeId, LeaveRequestDTO request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> ResourceNotFoundException.of("Employee", employeeId));

        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> ResourceNotFoundException.of("LeaveType", request.getLeaveTypeId()));

        if (!leaveRequestRepository.findOverlapping(employeeId, request.getStartDate(), request.getEndDate()).isEmpty()) {
            throw new InvalidLeaveRequestException(
                    "You already have a pending or approved leave request that overlaps these dates");
        }

        int totalDays = calculateWorkingDays(request.getStartDate(), request.getEndDate());
        if (totalDays <= 0) {
            throw new InvalidLeaveRequestException(
                    "The selected date range contains no working days (all weekends/holidays)");
        }


        if (!leaveBalanceService.hasSufficientBalance(employeeId, leaveType.getId(), totalDays)) {
            throw new InvalidLeaveRequestException(
                    "Insufficient " + leaveType.getName() + " balance for the requested " + totalDays + " day(s)");
        }

        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(request);
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setManager(employee.getManager());
        leaveRequest.setTotalDays(totalDays);

        boolean autoApprove = Boolean.FALSE.equals(leaveType.getRequiresApproval());
        if (autoApprove) {
            leaveRequest.setStatus(Status.APPROVED);
            leaveRequest.setApprovedAt(LocalDateTime.now());
        } else {
            leaveRequest.setStatus(Status.PENDING);
        }

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        if (autoApprove) {
            leaveBalanceService.adjustBalance(employeeId, leaveType.getId(), totalDays);
            notificationService.notifyLeaveApproved(saved);
        } else {
            notificationService.notifyLeaveApplied(saved);
        }
        auditService.log(employee, "APPLY", "LeaveRequest", saved.getId(), null, saved.getStatus().name());

        return leaveRequestMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LeaveRequestResponse approveLeave(Integer leaveId, Integer approverId) {
        LeaveRequest leaveRequest = findLeaveOrThrow(leaveId);
        requirePending(leaveRequest);
        requireAuthorizedApprover(leaveRequest, approverId);

        leaveBalanceService.adjustBalance(
                leaveRequest.getEmployee().getId(), leaveRequest.getLeaveType().getId(), leaveRequest.getTotalDays());

        leaveRequest.setStatus(Status.APPROVED);
        leaveRequest.setApprovedAt(LocalDateTime.now());
        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        notificationService.notifyLeaveApproved(saved);
        auditService.log(leaveRequest.getManager(), "APPROVE", "LeaveRequest", saved.getId(), "PENDING", "APPROVED");

        return leaveRequestMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LeaveRequestResponse rejectLeave(Integer leaveId, Integer approverId, String comments) {
        LeaveRequest leaveRequest = findLeaveOrThrow(leaveId);
        requirePending(leaveRequest);
        requireAuthorizedApprover(leaveRequest, approverId);

        leaveRequest.setStatus(Status.REJECTED);
        leaveRequest.setManagerComments(comments);
        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        notificationService.notifyLeaveRejected(saved);
        auditService.log(leaveRequest.getManager(), "REJECT", "LeaveRequest", saved.getId(), "PENDING", "REJECTED");

        return leaveRequestMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LeaveRequestResponse cancelLeave(Integer leaveId, Integer requesterId) {
        LeaveRequest leaveRequest = findLeaveOrThrow(leaveId);

        if (!leaveRequest.getEmployee().getId().equals(requesterId)) {
            throw new UnauthorizedActionException("You can only cancel your own leave requests");
        }
        if (leaveRequest.getStatus() != Status.PENDING && leaveRequest.getStatus() != Status.APPROVED) {
            throw new InvalidLeaveRequestException(
                    "Only pending or approved leave requests can be cancelled (current status: "
                            + leaveRequest.getStatus() + ")");
        }

        // Balance was only deducted once the leave was approved, so only restore it then.
        if (leaveRequest.getStatus() == Status.APPROVED) {
            leaveBalanceService.adjustBalance(
                    leaveRequest.getEmployee().getId(), leaveRequest.getLeaveType().getId(),
                    -leaveRequest.getTotalDays());
        }

        String previousStatus = leaveRequest.getStatus().name();
        leaveRequest.setStatus(Status.CANCELLED);
        leaveRequest.setCancelledAt(LocalDateTime.now());
        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        notificationService.notifyLeaveCancelled(saved);
        auditService.log(leaveRequest.getEmployee(), "CANCEL", "LeaveRequest", saved.getId(), previousStatus, "CANCELLED");

        return leaveRequestMapper.toResponse(saved);
    }

    @Override
    public LeaveRequestResponse getLeaveById(Integer leaveId) {
        return leaveRequestMapper.toResponse(findLeaveOrThrow(leaveId));
    }

    @Override
    public List<LeaveRequestResponse> getEmployeeLeaves(Integer employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId).stream()
                .map(leaveRequestMapper::toResponse)
                .toList();
    }

    private int calculateWorkingDays(LocalDate start, LocalDate end) {
        Set<LocalDate> holidayDates = new HashSet<>(
                holidayRepository.findByHolidayDateBetween(start, end).stream()
                        .map(h -> h.getHolidayDate())
                        .toList());

        int days = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
            if (!isWeekend && !holidayDates.contains(date)) {
                days++;
            }
        }
        return days;
    }

    private void requirePending(LeaveRequest leaveRequest) {
        if (leaveRequest.getStatus() != Status.PENDING) {
            throw new InvalidLeaveRequestException(
                    "Only pending leave requests can be actioned (current status: " + leaveRequest.getStatus() + ")");
        }
    }

    private void requireAuthorizedApprover(LeaveRequest leaveRequest, Integer approverId) {
        Employee manager = leaveRequest.getManager();
        if (manager == null || !manager.getId().equals(approverId)) {
            throw new UnauthorizedActionException("Only the assigned manager can action this leave request");
        }
    }

    private LeaveRequest findLeaveOrThrow(Integer id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("LeaveRequest", id));
    }
}
