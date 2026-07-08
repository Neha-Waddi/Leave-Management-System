package com.myprojects.leavemanagementsystem.controller;

import com.myprojects.leavemanagementsystem.dto.response.ApiResponse;
import com.myprojects.leavemanagementsystem.dto.response.AuditLogResponse;
import com.myprojects.leavemanagementsystem.service.interfaces.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditService auditService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getLogsByEmployee(
            @PathVariable Integer employeeId) {
        List<AuditLogResponse> response = auditService.getLogsByEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success("Audit logs fetched successfully", response));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/entity/{entityName}/{entityId}")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getLogsByEntity(
            @PathVariable String entityName, @PathVariable Integer entityId) {
        List<AuditLogResponse> response = auditService.getLogsByEntity(entityName, entityId);
        return ResponseEntity.ok(ApiResponse.success("Audit logs fetched successfully", response));
    }
}
