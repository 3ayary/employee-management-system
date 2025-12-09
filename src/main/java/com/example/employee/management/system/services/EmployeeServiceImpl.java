package com.example.employee.management.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.employee.management.system.abstracts.EmployeeService;
import com.example.employee.management.system.dtos.EmployeeCreate;
import com.example.employee.management.system.dtos.EmployeeUpdate;
import com.example.employee.management.system.entities.Department;
import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.repositories.DepartmentRepo;
import com.example.employee.management.system.repositories.EmployeeRepo;
import com.example.employee.management.system.shared.CustomResponseException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;
    private DepartmentRepo departmentRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public Employee findOne(UUID employeeId) {

        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found"));

        return employee;

    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee creatEmployee(EmployeeCreate employeeCreate) {

        Department department = departmentRepo.findById(employeeCreate.departmentId()).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + employeeCreate.departmentId() + " not found"));

        Employee employee = new Employee();

        employee.setFirstName(employeeCreate.firstName());

        employee.setLastName(employeeCreate.lastName());

        employee.setPosition(employeeCreate.position());

        employee.setHireDate(employeeCreate.hireDate());

        employee.setPhoneNumber(employeeCreate.phoneNumber());

        employee.setEmail(employeeCreate.email());

        employee.setDepartment(department);

        employeeRepo.save(employee);

        return employee;
    }

    @Override
    public void deleteEmployee(UUID employeeId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found"));

        employeeRepo.delete(employee);
    }

    @Override
    public Employee updateOne(UUID employeeId, EmployeeUpdate employeeUpdate) {

        Employee existingEmployee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(("employee with id " + employeeId + " not found")));

        Department department = departmentRepo.findById(employeeUpdate.departmentId()).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + employeeUpdate.departmentId() + " not found"));

        existingEmployee.setFirstName(employeeUpdate.firstName());
        existingEmployee.setLastName(employeeUpdate.lastName());
        existingEmployee.setPhoneNumber(employeeUpdate.phoneNumber());
        existingEmployee.setPosition(employeeUpdate.position());
        existingEmployee.setDepartment(department);
        employeeRepo.save(existingEmployee);

        return existingEmployee;
    }

}
