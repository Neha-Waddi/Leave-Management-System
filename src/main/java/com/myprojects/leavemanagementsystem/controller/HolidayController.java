package com.myprojects.leavemanagementsystem.controller;

import com.myprojects.leavemanagementsystem.dto.request.HolidayRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.ApiResponse;
import com.myprojects.leavemanagementsystem.dto.response.HolidayResponse;
import com.myprojects.leavemanagementsystem.service.interfaces.HolidayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @PostMapping
    public ResponseEntity<ApiResponse<HolidayResponse>> createHoliday(
            @Valid @RequestBody HolidayRequestDTO request) {
        HolidayResponse response = holidayService.createHoliday(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Holiday created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HolidayResponse>> getHolidayById(@PathVariable Integer id) {
        HolidayResponse response = holidayService.getHolidayById(id);
        return ResponseEntity.ok(ApiResponse.success("Holiday fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HolidayResponse>>> getAllHolidays() {
        List<HolidayResponse> response = holidayService.getAllHolidays();
        return ResponseEntity.ok(ApiResponse.success("Holidays fetched successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HolidayResponse>> updateHoliday(
            @PathVariable Integer id, @Valid @RequestBody HolidayRequestDTO request) {
        HolidayResponse response = holidayService.updateHoliday(id, request);
        return ResponseEntity.ok(ApiResponse.success("Holiday updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHoliday(@PathVariable Integer id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.ok(ApiResponse.success("Holiday deleted successfully", null));
    }
}
