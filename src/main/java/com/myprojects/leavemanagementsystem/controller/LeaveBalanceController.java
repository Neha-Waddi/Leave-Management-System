package com.myprojects.leavemanagementsystem.controller;

import com.myprojects.leavemanagementsystem.dto.request.LeaveBalanceRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.ApiResponse;
import com.myprojects.leavemanagementsystem.dto.response.LeaveBalanceResponse;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveBalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-balances")
@RequiredArgsConstructor
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse<LeaveBalanceResponse>> createLeaveBalance(
            @Valid @RequestBody LeaveBalanceRequestDTO request) {
        LeaveBalanceResponse response = leaveBalanceService.createLeaveBalance(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Leave balance created successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveBalanceResponse>>> getEmployeeBalances(
            @PathVariable Integer employeeId) {
        List<LeaveBalanceResponse> response = leaveBalanceService.getEmployeeBalances(employeeId);
        return ResponseEntity.ok(ApiResponse.success("Leave balances fetched successfully", response));
    }
}
