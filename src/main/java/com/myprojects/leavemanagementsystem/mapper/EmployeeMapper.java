package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.EmployeeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.EmployeeResponse;
import com.myprojects.leavemanagementsystem.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "reportees", ignore = true)
    Employee toEntity(EmployeeRequestDTO dto);

    // Same exclusions apply when applying an update onto an existing entity.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "reportees", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDto(EmployeeRequestDTO dto, @MappingTarget Employee entity);

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", expression = "java(fullName(entity.getManager()))")
    EmployeeResponse toResponse(Employee entity);

    default String fullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        String last = employee.getLastName() == null ? "" : " " + employee.getLastName();
        return employee.getFirstName() + last;
    }
}
