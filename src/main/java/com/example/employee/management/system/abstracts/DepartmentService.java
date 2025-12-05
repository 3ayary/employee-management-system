package com.example.employee.management.system.abstracts;

import java.util.List;
import java.util.UUID;

import com.example.employee.management.system.dtos.DepartmentCreate;
import com.example.employee.management.system.dtos.DepartmentUpdate;
import com.example.employee.management.system.entities.Department;

public interface DepartmentService {
    Department findOne(UUID departmentId);
    List<Department> findAll();
    Department createDepartment(DepartmentCreate department);
    void deleteOne(UUID departmentId); 
    Department updateDepartment(UUID departmentId, DepartmentUpdate departmentupdate);


}
