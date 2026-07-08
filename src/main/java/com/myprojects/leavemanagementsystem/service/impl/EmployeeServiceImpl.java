package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.aop.annotation.Audit;
import com.myprojects.leavemanagementsystem.dto.request.EmployeeRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.EmployeeResponse;
import com.myprojects.leavemanagementsystem.entity.Department;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.enums.EmployeeStatus;
import com.myprojects.leavemanagementsystem.exception.DuplicateResourceException;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.mapper.EmployeeMapper;
import com.myprojects.leavemanagementsystem.repository.DepartmentRepository;
import com.myprojects.leavemanagementsystem.repository.EmployeeRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.AuditService;
import com.myprojects.leavemanagementsystem.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    @Override
    @Transactional
    @Audit(action = "CREATE_EMPLOYEE")
    public EmployeeResponse createEmployee(EmployeeRequestDTO request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An employee with email '" + request.getEmail() + "' already exists");
        }
        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException(
                    "An employee with code '" + request.getEmployeeCode() + "' already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> ResourceNotFoundException.of("Department", request.getDepartmentId()));

        Employee manager = resolveManager(request.getManagerId());

        Employee employee = employeeMapper.toEntity(request);
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setDepartment(department);
        employee.setManager(manager);
        if (employee.getStatus() == null) {
            employee.setStatus(EmployeeStatus.ACTIVE);
        }

        Employee saved = employeeRepository.save(employee);
        auditService.log(saved, "CREATE", "Employee", saved.getId(), null, saved.getEmail());

        return employeeMapper.toResponse(saved);
    }

    @Override
    public EmployeeResponse getEmployeeById(Integer id) {
        return employeeMapper.toResponse(findEmployeeOrThrow(id));
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @Audit(action = "UPDATE_EMPLOYEE")
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequestDTO request) {
        Employee employee = findEmployeeOrThrow(id);

        boolean emailChanged = !employee.getEmail().equalsIgnoreCase(request.getEmail());
        if (emailChanged && employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An employee with email '" +  request.getEmail() + "' already exists");
        }

        boolean codeChanged = !employee.getEmployeeCode().equalsIgnoreCase(request.getEmployeeCode());
        if (codeChanged && employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException(
                    "An employee with code '" + request.getEmployeeCode() + "' already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> ResourceNotFoundException.of("Department", request.getDepartmentId()));

        if (request.getManagerId() != null && request.getManagerId().equals(id)) {
            throw new IllegalArgumentException("An employee cannot be their own manager");
        }
        Employee manager = resolveManager(request.getManagerId());

        employeeMapper.updateEntityFromDto(request, employee);
        employee.setDepartment(department);
        employee.setManager(manager);

        Employee saved = employeeRepository.save(employee);
        auditService.log(saved, "UPDATE", "Employee", saved.getId(), null, saved.getEmail());

        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    @Audit(action = "DELETE_EMPLOYEE")
    public void deleteEmployee(Integer id) {
        Employee employee = findEmployeeOrThrow(id);
        if (!employeeRepository.findByManagerId(id).isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete employee " + id + " while they still manage other employees; reassign reportees first");
        }
        employeeRepository.delete(employee);
        auditService.log(employee, "DELETE", "Employee", id, employee.getEmail(), null);
    }

    private Employee resolveManager(Integer managerId) {
        if (managerId == null) {
            return null;
        }
        return employeeRepository.findById(managerId)
                .orElseThrow(() -> ResourceNotFoundException.of("Manager (Employee)", managerId));
    }

    private Employee findEmployeeOrThrow(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Employee", id));
    }
}
