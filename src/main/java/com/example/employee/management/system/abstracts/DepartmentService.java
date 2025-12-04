package com.example.employee.management.system.abstracts;

import java.util.List;
import java.util.UUID;

import com.example.employee.management.system.entities.Department;

public interface DepartmentService {
    Department findOne(UUID departmentId);
    List<Department> findAll();
    Department createDepartment(Department department);
    void deleteOne(UUID departmentId); 


}
