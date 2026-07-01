package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.response.AuditLogResponse;
import com.myprojects.leavemanagementsystem.entity.AuditLog;
import com.myprojects.leavemanagementsystem.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", expression = "java(fullName(entity.getEmployee()))")
    AuditLogResponse toResponse(AuditLog entity);

    default String fullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        String last = employee.getLastName() == null ? "" : " " + employee.getLastName();
        return employee.getFirstName() + last;
    }
}
