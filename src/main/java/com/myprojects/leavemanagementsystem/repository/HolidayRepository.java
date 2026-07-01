package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    boolean existsByHolidayDate(LocalDate holidayDate);

    Optional<Holiday> findByHolidayDate(LocalDate holidayDate);

    List<Holiday> findByHolidayDateBetween(LocalDate start, LocalDate end);
}
