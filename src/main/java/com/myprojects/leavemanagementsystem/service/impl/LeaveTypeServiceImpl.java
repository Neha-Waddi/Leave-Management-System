package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.dto.request.LeaveTypeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveTypeResponse;
import com.myprojects.leavemanagementsystem.entity.LeaveType;
import com.myprojects.leavemanagementsystem.exception.DuplicateResourceException;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.mapper.LeaveTypeMapper;
import com.myprojects.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.myprojects.leavemanagementsystem.repository.LeaveTypeRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;
    private final LeaveBalanceRepository leaveBalanceRepository;

    @Override
    @Transactional
    public LeaveTypeResponse createLeaveType(LeaveTypeRequestDTO request) {
        if (leaveTypeRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("A leave type named '" + request.getName() + "' already exists");
        }

        LeaveType leaveType = leaveTypeMapper.toEntity(request);
        LeaveType saved = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponse(saved);
    }

    @Override
    public LeaveTypeResponse getLeaveTypeById(Integer id) {
        return leaveTypeMapper.toResponse(findLeaveTypeOrThrow(id));
    }

    @Override
    public List<LeaveTypeResponse> getAllLeaveTypes() {
        return leaveTypeRepository.findAll().stream()
                .map(leaveTypeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public LeaveTypeResponse updateLeaveType(Integer id, LeaveTypeRequestDTO request) {
        LeaveType leaveType = findLeaveTypeOrThrow(id);

        boolean nameChanged = !leaveType.getName().equalsIgnoreCase(request.getName());
        if (nameChanged && leaveTypeRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("A leave type named '" + request.getName() + "' already exists");
        }

        leaveTypeMapper.updateEntityFromDto(request, leaveType);
        LeaveType saved = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteLeaveType(Integer id) {
        LeaveType leaveType = findLeaveTypeOrThrow(id);
        if (leaveBalanceRepository.existsByLeaveTypeId(id)) {
            throw new IllegalStateException(
                    "Cannot delete leave type '" + leaveType.getName() + "' while leave balances reference it");
        }
        leaveTypeRepository.delete(leaveType);
    }

    private LeaveType findLeaveTypeOrThrow(Integer id) {
        return leaveTypeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("LeaveType", id));
    }
}
