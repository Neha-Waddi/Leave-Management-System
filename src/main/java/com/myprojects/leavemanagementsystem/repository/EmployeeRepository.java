package com.myprojects.leavemanagementsystem.repository;

import com.myprojects.leavemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

    boolean existsByEmployeeCode(String employeeCode);

    List<Employee> findByDepartmentId(Integer departmentId);

    List<Employee> findByManagerId(Integer managerId);
}
