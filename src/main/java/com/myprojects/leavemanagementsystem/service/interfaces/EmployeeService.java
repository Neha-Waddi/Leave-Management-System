package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.request.EmployeeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequestDTO request);

    EmployeeResponse getEmployeeById(Integer id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(Integer id, EmployeeRequestDTO request);

    void deleteEmployee(Integer id);
}
