package com.myprojects.leavemanagementsystem.dto.response;

import java.time.LocalDateTime;

public class LeaveTypeResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer totalDays;
    private Boolean carryForward;
    private Boolean requiresApproval;
    private LocalDateTime createdAt;
}
