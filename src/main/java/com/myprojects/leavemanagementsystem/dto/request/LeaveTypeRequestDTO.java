package com.myprojects.leavemanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTypeRequestDTO {

    @NotBlank(message = "Leave type name is required")
    @Size(max = 50, message = "Leave type name must not exceed 50 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Total days is required")
    @Positive(message = "Total days must be greater than zero")
    private Integer totalDays;

    @NotNull(message = "Carry forward flag is required")
    private Boolean carryForward;

    @NotNull(message = "Requires approval flag is required")
    private Boolean requiresApproval;
}
