package com.myprojects.leavemanagementsystem.security.controller;

import com.myprojects.leavemanagementsystem.security.dto.LoginRequestDTO;
import com.myprojects.leavemanagementsystem.security.dto.LoginResponseDTO;
import com.myprojects.leavemanagementsystem.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response = authenticationService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}