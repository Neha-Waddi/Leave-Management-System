package com.myprojects.leavemanagementsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceRequestDTO {

    @NotNull(message = "Employee id is required")
    private Integer employeeId;

    @NotNull(message = "Leave type id is required")
    private Integer leaveTypeId;

    @NotNull(message = "Total balance is required")
    @PositiveOrZero(message = "Total balance cannot be negative")
    private Integer totalBalance;

    @PositiveOrZero(message = "Used balance cannot be negative")
    private Integer usedBalance;
}
