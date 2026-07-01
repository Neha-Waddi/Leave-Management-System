package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.request.LeaveTypeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveTypeResponse;

import java.util.List;

public interface LeaveTypeService {

    LeaveTypeResponse createLeaveType(LeaveTypeRequestDTO request);

    LeaveTypeResponse getLeaveTypeById(Integer id);

    List<LeaveTypeResponse> getAllLeaveTypes();

    LeaveTypeResponse updateLeaveType(Integer id, LeaveTypeRequestDTO request);

    void deleteLeaveType(Integer id);
}
