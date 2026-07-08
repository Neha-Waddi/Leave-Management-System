package com.myprojects.leavemanagementsystem.aop.aspect;

import com.myprojects.leavemanagementsystem.aop.annotation.Audit;
import com.myprojects.leavemanagementsystem.entity.AuditLog;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.repository.AuditLogRepository;
import com.myprojects.leavemanagementsystem.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditLogRepository auditLogRepository;
    private final EmployeeRepository employeeRepository;
    private final HttpServletRequest request;

    @AfterReturning("@annotation(audit)")
    public void auditLog(
            JoinPoint joinPoint,
            Audit audit) {

        log.info("Action : {}", audit.action());

        log.info("Method : {}",
                joinPoint.getSignature().getName());

        AuditLog auditLog = new AuditLog();
        auditLog.setAction(audit.action());

        auditLog.setEntityName(
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName());

        auditLog.setTimestamp(LocalDateTime.now());

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();

        Employee employee=employeeRepository.findByEmail(email).orElse(null);
        String ip=request.getRemoteAddr();

        auditLog.setEmployee(employee);
        auditLog.setIpAddress(ip);
        auditLogRepository.save(auditLog);

    }
}