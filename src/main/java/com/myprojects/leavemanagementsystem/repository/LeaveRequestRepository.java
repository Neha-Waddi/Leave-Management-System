package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByEmployeeId(Integer employeeId);

    List<LeaveRequest> findByEmployeeIdAndStatus(Integer employeeId, LeaveStatus status);

    List<LeaveRequest> findByManagerIdAndStatus(Integer managerId, LeaveStatus status);


    @Query("""
            SELECT lr FROM LeaveRequest lr
            WHERE lr.employee.id = :employeeId
              AND lr.status IN (com.myprojects.leavemanagementsystem.enums.LeaveStatus.PENDING,
                                 com.myprojects.leavemanagementsystem.enums.LeaveStatus.APPROVED)
              AND lr.startDate <= :endDate
              AND lr.endDate >= :startDate
            """)
    List<LeaveRequest> findOverlapping(@Param("employeeId") Integer employeeId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    List<LeaveRequest> findByStatusAndAppliedAtBefore(
            LeaveStatus status,
            LocalDateTime dateTime
    );
}
