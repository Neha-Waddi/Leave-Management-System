package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Integer> {
    Optional<Holiday> findByHolidayDate(LocalDate holidayDate);
}
