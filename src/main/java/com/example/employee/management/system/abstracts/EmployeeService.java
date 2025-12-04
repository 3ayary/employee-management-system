package com.example.employee.management.system.abstracts;

import java.util.List;
import java.util.UUID;

import com.example.employee.management.system.dtos.EmployeeCreate;
import com.example.employee.management.system.dtos.EmployeeUpdate;
import com.example.employee.management.system.entities.Employee;

public interface EmployeeService {

    Employee findOne(UUID employeeId);

    List<Employee> findAll();

    Employee creatEmployee(EmployeeCreate employee);

    void deleteEmployee(UUID employeeId);

    Employee updateOne(UUID employeeId, EmployeeUpdate employee);
}
