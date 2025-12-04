package com.example.employee.management.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.employee.management.system.abstracts.DepartmentService;
import com.example.employee.management.system.entities.Department;
import com.example.employee.management.system.repositories.DepartmentRepo;
import com.example.employee.management.system.shared.CustomResponseException;

@Service
public class DeparmentServiceImpl implements DepartmentService {

    private DepartmentRepo departmentRepo;

    DeparmentServiceImpl(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @Override
    public Department findOne(UUID departmentId) {

        return departmentRepo.findById(departmentId).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + departmentId + " not found"));
    }

    @Override
    public List<Department> findAll() {

        return departmentRepo.findAll();
    }

    // @Override
    // public Department createDepartment(Department department) {

    //     // return departmentRepo.findById(departmentId).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + departmentId + " not found"));
    // }

    @Override
    public void deleteOne(UUID departmentId) {

        throw new UnsupportedOperationException("Unimplemented method 'deleteOne'");
    }

}
