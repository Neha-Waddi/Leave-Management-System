package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.request.DepartmentRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequestDTO request);

    DepartmentResponse getDepartmentById(Integer id);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(Integer id, DepartmentRequestDTO request);

    void deleteDepartment(Integer id);
}
