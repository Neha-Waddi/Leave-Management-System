package com.myprojects.leavemanagementsystem.security.service;

import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.repository.EmployeeRepository;
import com.myprojects.leavemanagementsystem.security.dto.LoginRequestDTO;
import com.myprojects.leavemanagementsystem.security.dto.LoginResponseDTO;
import com.myprojects.leavemanagementsystem.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        Employee employee = employeeRepository
                .findByEmail(request.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("Employee not found"));

        String token = jwtService.generateToken(employee);

        return new LoginResponseDTO(
                token,
                "Bearer",
                employee.getId(),
                employee.getEmail(),
                employee.getRole().name()
        );
    }
}