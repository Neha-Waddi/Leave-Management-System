package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.entity.Employee;

public interface AuditService {

    void log(Employee actor, String action, String entityName, Integer entityId,
             String oldValue, String newValue);
}
