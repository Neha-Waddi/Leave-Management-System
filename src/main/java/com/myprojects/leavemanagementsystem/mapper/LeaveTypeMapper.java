package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.LeaveTypeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.LeaveTypeResponse;
import com.myprojects.leavemanagementsystem.entity.LeaveType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaveTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    LeaveType toEntity(LeaveTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(LeaveTypeRequestDTO dto, @MappingTarget LeaveType entity);

    LeaveTypeResponse toResponse(LeaveType entity);
}
