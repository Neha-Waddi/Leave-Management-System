package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.response.LeaveBalanceResponse;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.entity.LeaveBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveBalanceMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", expression = "java(fullName(entity.getEmployee()))")
    @Mapping(target = "leaveTypeId", source = "leaveType.id")
    @Mapping(target = "leaveTypeName", source = "leaveType.name")
    LeaveBalanceResponse toResponse(LeaveBalance entity);

    default String fullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        String last = employee.getLastName() == null ? "" : " " + employee.getLastName();
        return employee.getFirstName() + last;
    }
}
