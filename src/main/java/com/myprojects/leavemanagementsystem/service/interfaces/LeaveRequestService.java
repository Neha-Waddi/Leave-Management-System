package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.request.LeaveRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveRequestResponse;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequestResponse applyLeave(Integer employeeId, LeaveRequestDTO request);

    LeaveRequestResponse approveLeave(Integer leaveId, Integer approverId);

    LeaveRequestResponse rejectLeave(Integer leaveId, Integer approverId, String comments);

    LeaveRequestResponse cancelLeave(Integer leaveId, Integer requesterId);

    LeaveRequestResponse getLeaveById(Integer leaveId);

    List<LeaveRequestResponse> getEmployeeLeaves(Integer employeeId);

    List<LeaveRequestResponse> getPendingApprovalsForManager(Integer managerId);
}
