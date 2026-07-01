package com.myprojects.leavemanagementsystem.dto.response;

import com.myprojects.leavemanagementsystem.enums.Role;

public class LoginResponse {
    private String token;
    private String type;
    private String email;
    private Role role;
}
