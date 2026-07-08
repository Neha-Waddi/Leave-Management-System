package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.aop.annotation.Audit;
import com.myprojects.leavemanagementsystem.dto.request.LeaveBalanceRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveBalanceResponse;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.entity.LeaveBalance;
import com.myprojects.leavemanagementsystem.entity.LeaveType;
import com.myprojects.leavemanagementsystem.exception.DuplicateResourceException;
import com.myprojects.leavemanagementsystem.exception.InsufficientLeaveBalanceException;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.mapper.LeaveBalanceMapper;
import com.myprojects.leavemanagementsystem.repository.EmployeeRepository;
import com.myprojects.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.myprojects.leavemanagementsystem.repository.LeaveTypeRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceMapper leaveBalanceMapper;

    @Override
    @Transactional
    @Audit(action = "CREATE_LEAVE_BALANCE")
    public LeaveBalanceResponse createLeaveBalance(LeaveBalanceRequestDTO request) {
        if (leaveBalanceRepository.existsByEmployeeIdAndLeaveTypeId(request.getEmployeeId(), request.getLeaveTypeId())) {
            throw new DuplicateResourceException(
                    "A leave balance already exists for employee " + request.getEmployeeId()
                            + " and leave type " + request.getLeaveTypeId());
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> ResourceNotFoundException.of("Employee", request.getEmployeeId()));
        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> ResourceNotFoundException.of("LeaveType", request.getLeaveTypeId()));

        int used = request.getUsedBalance() != null ? request.getUsedBalance() : 0;
        if (used > request.getTotalBalance()) {
            throw new IllegalArgumentException("Used balance cannot exceed total balance");
        }

        LeaveBalance balance = new LeaveBalance();
        balance.setEmployee(employee);
        balance.setLeaveType(leaveType);
        balance.setTotalBalance(request.getTotalBalance());
        balance.setUsedBalance(used);
        balance.setRemainingBalance(request.getTotalBalance() - used);

        LeaveBalance saved = leaveBalanceRepository.save(balance);
        return leaveBalanceMapper.toResponse(saved);
    }

    @Override
    public List<LeaveBalanceResponse> getEmployeeBalances(Integer employeeId) {
        return leaveBalanceRepository.findByEmployeeId(employeeId).stream()
                .map(leaveBalanceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @Audit(action = "ADJUST_LEAVE_BALANCE")
    public void adjustBalance(Integer employeeId, Integer leaveTypeId, int deltaDays) {
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeId(employeeId, leaveTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No leave balance found for employee " + employeeId + " and leave type " + leaveTypeId));

        int newUsed = balance.getUsedBalance() + deltaDays;
        int newRemaining = balance.getTotalBalance() - newUsed;

        if (newUsed < 0 || newRemaining < 0) {
            throw new InsufficientLeaveBalanceException(
                    "Insufficient leave balance: requested adjustment of " + deltaDays
                            + " days would leave the balance in an invalid state");
        }

        balance.setUsedBalance(newUsed);
        balance.setRemainingBalance(newRemaining);
        leaveBalanceRepository.save(balance);
    }

    @Override
    public boolean hasSufficientBalance(Integer employeeId, Integer leaveTypeId, int requiredDays) {
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeId(employeeId, leaveTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No leave balance found for employee " + employeeId + " and leave type " + leaveTypeId));
        return balance.getRemainingBalance() >= requiredDays;
    }
}
