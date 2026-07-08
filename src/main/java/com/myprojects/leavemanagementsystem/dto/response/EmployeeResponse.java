package com.myprojects.leavemanagementsystem.dto.response;

import com.myprojects.leavemanagementsystem.enums.EmployeeStatus;
import com.myprojects.leavemanagementsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Integer id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String designation;
    private LocalDate joiningDate;
    private Role role;
    private EmployeeStatus status;

    private Integer departmentId;
    private String departmentName;

    private Integer managerId;
    private String managerName;
}
