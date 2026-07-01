package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByEmployeeId(Integer employeeId);

    List<AuditLog> findByEntityNameAndEntityId(String entityName, Integer entityId);
}
