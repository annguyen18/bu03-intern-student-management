package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.Department;
import com.viettel.intern.repository.base.DepartmentRepository;
import com.viettel.intern.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartment() { return departmentRepository.findAll(); }
}
