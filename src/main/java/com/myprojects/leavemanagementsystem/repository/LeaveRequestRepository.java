package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    List<LeaveRequest> findByManagerId(Long managerId);

    List<LeaveRequest> findByStatus(Status status);

    List<LeaveRequest> findByEmployeeIdAndStatus(
            Long employeeId,
            Status status);

}
