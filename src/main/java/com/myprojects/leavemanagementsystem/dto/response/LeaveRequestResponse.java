package com.myprojects.leavemanagementsystem.dto.response;

import com.myprojects.leavemanagementsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestResponse {
    private Integer id;

    private Integer employeeId;
    private String employeeName;

    private Integer managerId;
    private String managerName;

    private Integer leaveTypeId;
    private String leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;

    private String reason;
    private Status status;

    private String managerComments;

    private LocalDateTime appliedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime cancelledAt;
}
