package com.example.employee.management.system.abstracts;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.employee.management.system.dtos.EmployeeCreate;
import com.example.employee.management.system.dtos.EmployeeUpdate;
import com.example.employee.management.system.entities.Employee;

public interface EmployeeService {

    Employee findOne(UUID employeeId);

    Page<Employee> findAll(int page,int size);

    Employee creatEmployee(EmployeeCreate employee);

    void deleteEmployee(UUID employeeId);

    Employee updateOne(UUID employeeId, EmployeeUpdate employee);
}
