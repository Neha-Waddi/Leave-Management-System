package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeId(Integer employeeId, Integer leaveTypeId);

    List<LeaveBalance> findByEmployeeId(Integer employeeId);

    boolean existsByEmployeeIdAndLeaveTypeId(Integer employeeId, Integer leaveTypeId);

    boolean existsByLeaveTypeId(Integer leaveTypeId);
}
