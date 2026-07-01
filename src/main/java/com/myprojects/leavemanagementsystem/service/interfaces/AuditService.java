package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.response.AuditLogResponse;
import com.myprojects.leavemanagementsystem.entity.Employee;

import java.util.List;

public interface AuditService {

    void log(Employee actor, String action, String entityName, Integer entityId,
             String oldValue, String newValue);

    List<AuditLogResponse> getLogsByEmployee(Integer employeeId);

    List<AuditLogResponse> getLogsByEntity(String entityName, Integer entityId);
}
