package com.myprojects.leavemanagementsystem.dto.response;

import com.myprojects.leavemanagementsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type;
    private String email;
    private Role role;
}
