package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.DepartmentRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.DepartmentResponse;
import com.myprojects.leavemanagementsystem.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentRequestDTO request);

    DepartmentResponse toResponse(Department department);
}