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
public class LeaveTypeResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer totalDays;
    private Boolean carryForward;
    private Boolean requiresApproval;
    private LocalDateTime createdAt;
}
