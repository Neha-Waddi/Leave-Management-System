package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.request.LeaveBalanceRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveBalanceResponse;

import java.util.List;

public interface LeaveBalanceService {

    LeaveBalanceResponse createLeaveBalance(LeaveBalanceRequestDTO request);

    List<LeaveBalanceResponse> getEmployeeBalances(Integer employeeId);

    void adjustBalance(Integer employeeId, Integer leaveTypeId, int deltaDays);

    boolean hasSufficientBalance(Integer employeeId, Integer leaveTypeId, int requiredDays);
}
