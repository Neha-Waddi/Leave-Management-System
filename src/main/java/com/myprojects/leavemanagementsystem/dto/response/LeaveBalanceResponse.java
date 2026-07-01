package com.myprojects.leavemanagementsystem.dto.response;

import java.time.LocalDateTime;

public class LeaveBalanceResponse {
    private Integer id;
    private Integer employeeId;
    private String employeeName;

    private Integer leaveTypeId;
    private String leaveTypeName;

    private Integer totalBalance;
    private Integer usedBalance;
    private Integer remainingBalance;

    private LocalDateTime lastUpdated;
}
