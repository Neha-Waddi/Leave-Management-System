package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.aop.annotation.Audit;
import com.myprojects.leavemanagementsystem.dto.request.HolidayRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.HolidayResponse;
import com.myprojects.leavemanagementsystem.entity.Holiday;
import com.myprojects.leavemanagementsystem.exception.DuplicateResourceException;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.mapper.HolidayMapper;
import com.myprojects.leavemanagementsystem.repository.HolidayRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;

    @Override
    @Transactional
    @Audit(action = "CREATE_HOLIDAY")
    public HolidayResponse createHoliday(HolidayRequestDTO request) {
        if (holidayRepository.existsByHolidayDate(request.getHolidayDate())) {
            throw new DuplicateResourceException(
                    "A holiday is already recorded for " + request.getHolidayDate());
        }

        Holiday holiday = holidayMapper.toEntity(request);
        Holiday saved = holidayRepository.save(holiday);
        return holidayMapper.toResponse(saved);
    }

    @Override
    public HolidayResponse getHolidayById(Integer id) {
        return holidayMapper.toResponse(findHolidayOrThrow(id));
    }

    @Override
    public List<HolidayResponse> getAllHolidays() {
        return holidayRepository.findAll().stream()
                .map(holidayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @Audit(action = "UPDATE_HOLIDAY")
    public HolidayResponse updateHoliday(Integer id, HolidayRequestDTO request) {
        Holiday holiday = findHolidayOrThrow(id);

        boolean dateChanged = !holiday.getHolidayDate().equals(request.getHolidayDate());
        if (dateChanged && holidayRepository.existsByHolidayDate(request.getHolidayDate())) {
            throw new DuplicateResourceException(
                    "A holiday is already recorded for " + request.getHolidayDate());
        }

        holidayMapper.updateEntityFromDto(request, holiday);
        Holiday saved = holidayRepository.save(holiday);
        return holidayMapper.toResponse(saved);
    }

    @Override
    @Transactional
    @Audit(action = "DELETE_HOLIDAY")
    public void deleteHoliday(Integer id) {
        Holiday holiday = findHolidayOrThrow(id);
        holidayRepository.delete(holiday);
    }

    private Holiday findHolidayOrThrow(Integer id) {
        return holidayRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Holiday", id));
    }
}
