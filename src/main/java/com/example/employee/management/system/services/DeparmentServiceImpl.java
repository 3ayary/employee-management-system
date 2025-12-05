package com.example.employee.management.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.employee.management.system.abstracts.DepartmentService;
import com.example.employee.management.system.dtos.DepartmentCreate;
import com.example.employee.management.system.dtos.DepartmentUpdate;
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

    @Override
    public Department createDepartment(DepartmentCreate departmentCreate) {
        Department department = new Department();

        department.setName(departmentCreate.name());

        departmentRepo.save(department);

        return department;
    }

    @Override
    public void deleteOne(UUID departmentId) {

        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + departmentId + "not found"));

        departmentRepo.delete(department);
    }

    @Override
    public Department updateDepartment(UUID departmentId, DepartmentUpdate departmentupdate) {

        Department existingDepartment = departmentRepo.findById(departmentId).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + departmentId + "not found"));

        existingDepartment.setName(departmentupdate.name());
        departmentRepo.save(existingDepartment);

        return existingDepartment;
    }
}
