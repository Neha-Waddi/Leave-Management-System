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
public class AuditLogResponse {

    private Integer id;

    private Integer employeeId;
    private String employeeName;

    private String action;
    private String entityName;
    private Integer entityId;

    private String oldValue;
    private String newValue;

    private String ipAddress;
    private LocalDateTime timestamp;
}
