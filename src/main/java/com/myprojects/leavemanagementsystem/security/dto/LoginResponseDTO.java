package com.myprojects.leavemanagementsystem.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private String accessToken;
    private String tokenType;
    private Integer employeeId;
    private String email;
    private String role;
}