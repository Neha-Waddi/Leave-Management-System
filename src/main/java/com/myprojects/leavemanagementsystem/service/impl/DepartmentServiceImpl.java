package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.aop.annotation.Audit;
import com.myprojects.leavemanagementsystem.dto.request.DepartmentRequestDTO;
import com.myprojects.leavemanagementsystem.dto.response.DepartmentResponse;
import com.myprojects.leavemanagementsystem.entity.Department;
import com.myprojects.leavemanagementsystem.exception.DuplicateResourceException;
import com.myprojects.leavemanagementsystem.exception.ResourceNotFoundException;
import com.myprojects.leavemanagementsystem.mapper.DepartmentMapper;
import com.myprojects.leavemanagementsystem.repository.DepartmentRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional
    @Audit(action = "CREATE_DEPARTMENT")
    public DepartmentResponse createDepartment(DepartmentRequestDTO request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("A department named '" + request.getName() + "' already exists");
        }

        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);
    }

    @Override
    public DepartmentResponse getDepartmentById(Integer id) {
        Department department = findDepartmentOrThrow(id);
        return departmentMapper.toResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @Audit(action = "UPDATE_DEPARTMENT")
    public DepartmentResponse updateDepartment(Integer id, DepartmentRequestDTO request) {
        Department department = findDepartmentOrThrow(id);

        boolean nameChanged = !department.getName().equalsIgnoreCase(request.getName());
        if (nameChanged && departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("A department named '" + request.getName() + "' already exists");
        }

        departmentMapper.updateEntityFromDto(request, department);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    @Audit(action = "DELETE_DEPARTMENT")
    public void deleteDepartment(Integer id) {
        Department department = findDepartmentOrThrow(id);
        if (!department.getEmployees().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete department '" + department.getName() + "' while it still has employees assigned");
        }
        departmentRepository.delete(department);
    }

    private Department findDepartmentOrThrow(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Department", id));
    }
}
