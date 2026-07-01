package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.HolidayRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.HolidayResponse;
import com.myprojects.leavemanagementsystem.entity.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Holiday toEntity(HolidayRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(HolidayRequestDTO dto, @MappingTarget Holiday entity);

    HolidayResponse toResponse(Holiday entity);
}
