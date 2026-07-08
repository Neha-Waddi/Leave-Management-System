package com.myprojects.leavemanagementsystem.controller;

import com.myprojects.leavemanagementsystem.dto.request.LeaveRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.ApiResponse;
import com.myprojects.leavemanagementsystem.dto.response.LeaveRequestResponse;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-requests")
@RequiredArgsConstructor
@Validated
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping("/apply/{employeeId}")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> applyLeave(
            @PathVariable Integer employeeId, @Valid @RequestBody LeaveRequestDTO request) {
        LeaveRequestResponse response = leaveRequestService.applyLeave(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Leave request submitted successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> approveLeave(
            @PathVariable Integer leaveId, @RequestParam Integer approverId) {
        LeaveRequestResponse response = leaveRequestService.approveLeave(leaveId, approverId);
        return ResponseEntity.ok(ApiResponse.success("Leave request approved successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> rejectLeave(
            @PathVariable Integer leaveId, @RequestParam Integer approverId,
            @RequestParam @NotBlank String comments) {
        LeaveRequestResponse response = leaveRequestService.rejectLeave(leaveId, approverId, comments);
        return ResponseEntity.ok(ApiResponse.success("Leave request rejected successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PutMapping("/{leaveId}/cancel")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> cancelLeave(
            @PathVariable Integer leaveId, @RequestParam Integer requesterId) {
        LeaveRequestResponse response = leaveRequestService.cancelLeave(leaveId, requesterId);
        return ResponseEntity.ok(ApiResponse.success("Leave request cancelled successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{leaveId}")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> getLeaveById(@PathVariable Integer leaveId) {
        LeaveRequestResponse response = leaveRequestService.getLeaveById(leaveId);
        return ResponseEntity.ok(ApiResponse.success("Leave request fetched successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveRequestResponse>>> getEmployeeLeaves(
            @PathVariable Integer employeeId) {
        List<LeaveRequestResponse> response = leaveRequestService.getEmployeeLeaves(employeeId);
        return ResponseEntity.ok(ApiResponse.success("Employee leave requests fetched successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/manager/{managerId}/pending")
    public ResponseEntity<ApiResponse<List<LeaveRequestResponse>>> getPendingApprovalsForManager(
            @PathVariable Integer managerId) {
        List<LeaveRequestResponse> response = leaveRequestService.getPendingApprovalsForManager(managerId);
        return ResponseEntity.ok(ApiResponse.success("Pending approvals fetched successfully", response));
    }
}
