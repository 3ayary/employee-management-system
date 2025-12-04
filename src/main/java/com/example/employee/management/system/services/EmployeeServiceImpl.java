package com.example.employee.management.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.employee.management.system.abstracts.EmployeeService;
import com.example.employee.management.system.dtos.EmployeeCreate;
import com.example.employee.management.system.dtos.EmployeeUpdate;
import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.repositories.EmployeeRepo;
import com.example.employee.management.system.shared.CustomResponseException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
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

        Employee employee = new Employee();

        employee.setDepartmentId(UUID.randomUUID());

        employee.setFirstName(employeeCreate.firstName());

        employee.setLastName(employeeCreate.lastName());

        employee.setPosition(employeeCreate.position());

        employee.setHireDate(employeeCreate.hireDate());

        employee.setPhoneNumber(employeeCreate.phoneNumber());

        employee.setEmail(employeeCreate.email());

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
    public Employee updateOne(UUID employeeId, EmployeeUpdate employeeCreate) {

        Employee existingEmployee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(("employee with id " + employeeId + " not found")));

        existingEmployee.setFirstName(employeeCreate.firstName());
        existingEmployee.setLastName(employeeCreate.lastName());
        existingEmployee.setPhoneNumber(employeeCreate.phoneNumber());
        existingEmployee.setPosition(employeeCreate.position());

        employeeRepo.save(existingEmployee);

        return existingEmployee;
    }

}
