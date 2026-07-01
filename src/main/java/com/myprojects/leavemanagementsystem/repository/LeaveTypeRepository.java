package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {

    Optional<LeaveType> findByName(String name);

    boolean existsByName(String name);
}
