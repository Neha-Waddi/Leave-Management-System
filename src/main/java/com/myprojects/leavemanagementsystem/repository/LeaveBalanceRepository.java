package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeId(
            Long employeeId,
            Long leaveTypeId);

    List<LeaveBalance> findByEmployeeId(Long employeeId);

}