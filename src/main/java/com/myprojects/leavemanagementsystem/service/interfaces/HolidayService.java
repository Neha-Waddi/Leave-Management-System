package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.dto.request.HolidayRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.HolidayResponse;

import java.util.List;

public interface HolidayService {

    HolidayResponse createHoliday(HolidayRequestDTO request);

    HolidayResponse getHolidayById(Integer id);

    List<HolidayResponse> getAllHolidays();

    HolidayResponse updateHoliday(Integer id, HolidayRequestDTO request);

    void deleteHoliday(Integer id);
}
