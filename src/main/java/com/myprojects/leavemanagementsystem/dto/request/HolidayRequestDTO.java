package com.myprojects.leavemanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequestDTO {

    @NotBlank(message = "Holiday name is required")
    @Size(max = 100, message = "Holiday name must not exceed 100 characters")
    private String holidayName;

    @NotNull(message = "Holiday date is required")
    private LocalDate holidayDate;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
