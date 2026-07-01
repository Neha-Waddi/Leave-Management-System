package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.LeaveRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveRequestResponse;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {

    // employee, leaveType, manager, status, totalDays and the timestamp fields
    // are all resolved or computed in the service layer, so they're excluded here.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "leaveType", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "totalDays", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "managerComments", ignore = true)
    @Mapping(target = "appliedAt", ignore = true)
    @Mapping(target = "approvedAt", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    LeaveRequest toEntity(LeaveRequestDTO dto);

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", expression = "java(fullName(entity.getEmployee()))")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", expression = "java(fullName(entity.getManager()))")
    @Mapping(target = "leaveTypeId", source = "leaveType.id")
    @Mapping(target = "leaveType", source = "leaveType.name")
    LeaveRequestResponse toResponse(LeaveRequest entity);

    default String fullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        String last = employee.getLastName() == null ? "" : " " + employee.getLastName();
        return employee.getFirstName() + last;
    }
}
