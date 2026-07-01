package com.myprojects.leavemanagementsystem.mapper;

import com.myprojects.leavemanagementsystem.dto.request.HolidayRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.HolidayResponse;
import com.myprojects.leavemanagementsystem.entity.Holiday;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    Holiday toEntity(HolidayRequestDTO request);

    HolidayResponse toResponse(Holiday holiday);
}
