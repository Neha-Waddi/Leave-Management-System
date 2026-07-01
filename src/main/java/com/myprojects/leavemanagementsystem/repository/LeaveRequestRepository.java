package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByEmployeeId(Integer employeeId);

    List<LeaveRequest> findByEmployeeIdAndStatus(Integer employeeId, Status status);

    List<LeaveRequest> findByManagerIdAndStatus(Integer managerId, Status status);

    /**
     * Finds any existing leave request for the employee that overlaps the given
     * date range and is not already rejected/cancelled. Used to block double-booking.
     */
    @Query("""
            SELECT lr FROM LeaveRequest lr
            WHERE lr.employee.id = :employeeId
              AND lr.status IN (com.myprojects.leavemanagementsystem.enums.Status.PENDING,
                                 com.myprojects.leavemanagementsystem.enums.Status.APPROVED)
              AND lr.startDate <= :endDate
              AND lr.endDate >= :startDate
            """)
    List<LeaveRequest> findOverlapping(@Param("employeeId") Integer employeeId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);
}
