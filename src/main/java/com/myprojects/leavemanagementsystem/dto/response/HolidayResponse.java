package com.myprojects.leavemanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayResponse {
    private Integer id;
    private String holidayName;
    private LocalDate holidayDate;
    private String description;
    private LocalDateTime createdAt;
}
