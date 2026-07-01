package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.LeaveTypeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveTypeResponse;
import com.myprojects.leavemanagementsystem.entity.LeaveType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeaveTypeMapper {

    LeaveType toEntity(LeaveTypeRequestDTO request);

    LeaveTypeResponse toResponse(LeaveType leaveType);
}
