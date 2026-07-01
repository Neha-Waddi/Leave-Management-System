package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.entity.AuditLog;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.repository.AuditLogRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void log(Employee actor, String action, String entityName, Integer entityId,
                     String oldValue, String newValue) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setEmployee(actor);
            auditLog.setAction(action);
            auditLog.setEntityName(entityName);
            auditLog.setEntityId(entityId);
            auditLog.setOldValue(oldValue);
            auditLog.setNewValue(newValue);
            auditLogRepository.save(auditLog);
        } catch (Exception ex) {

            log.error("Failed to write audit log for action='{}' entity='{}' id={}",
                    action, entityName, entityId, ex);
        }
    }
}
