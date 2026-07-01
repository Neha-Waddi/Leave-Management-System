package com.myprojects.leavemanagementsystem.controller;

import com.myprojects.leavemanagementsystem.dto.request.LeaveTypeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.ApiResponse;
import com.myprojects.leavemanagementsystem.dto.response.LeaveTypeResponse;
import com.myprojects.leavemanagementsystem.service.interfaces.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse<LeaveTypeResponse>> createLeaveType(
            @Valid @RequestBody LeaveTypeRequestDTO request) {
        LeaveTypeResponse response = leaveTypeService.createLeaveType(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Leave type created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveTypeResponse>> getLeaveTypeById(@PathVariable Integer id) {
        LeaveTypeResponse response = leaveTypeService.getLeaveTypeById(id);
        return ResponseEntity.ok(ApiResponse.success("Leave type fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveTypeResponse>>> getAllLeaveTypes() {
        List<LeaveTypeResponse> response = leaveTypeService.getAllLeaveTypes();
        return ResponseEntity.ok(ApiResponse.success("Leave types fetched successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveTypeResponse>> updateLeaveType(
            @PathVariable Integer id, @Valid @RequestBody LeaveTypeRequestDTO request) {
        LeaveTypeResponse response = leaveTypeService.updateLeaveType(id, request);
        return ResponseEntity.ok(ApiResponse.success("Leave type updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLeaveType(@PathVariable Integer id) {
        leaveTypeService.deleteLeaveType(id);
        return ResponseEntity.ok(ApiResponse.success("Leave type deleted successfully", null));
    }
}
