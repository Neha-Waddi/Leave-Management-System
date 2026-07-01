package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.dto.response.AuditLogResponse;
import com.myprojects.leavemanagementsystem.entity.AuditLog;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.mapper.AuditLogMapper;
import com.myprojects.leavemanagementsystem.repository.AuditLogRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

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

    @Override
    public List<AuditLogResponse> getLogsByEmployee(Integer employeeId) {
        return auditLogRepository.findByEmployeeId(employeeId).stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> getLogsByEntity(String entityName, Integer entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId).stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }
}
