package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.DepartmentRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.DepartmentResponse;
import com.myprojects.leavemanagementsystem.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Department toEntity(DepartmentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(DepartmentRequestDTO dto, @MappingTarget Department entity);

    DepartmentResponse toResponse(Department entity);
}
