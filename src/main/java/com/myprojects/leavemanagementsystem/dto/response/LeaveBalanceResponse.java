package com.myprojects.leavemanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
