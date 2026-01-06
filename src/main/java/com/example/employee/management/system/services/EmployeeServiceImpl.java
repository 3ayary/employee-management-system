package com.example.employee.management.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.employee.management.system.abstracts.EmployeeService;
import com.example.employee.management.system.dtos.EmployeeCreate;
import com.example.employee.management.system.dtos.EmployeeUpdate;
import com.example.employee.management.system.entities.Department;
import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.enums.Role;
import com.example.employee.management.system.repositories.DepartmentRepo;
import com.example.employee.management.system.repositories.EmployeeRepo;
import com.example.employee.management.system.shared.CustomResponseException;
import com.example.employee.management.system.utils.SecurityUtils;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;
    private DepartmentRepo departmentRepo;
    private EmailService emailService;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo, EmailService emailService) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.emailService = emailService;
    }

    @Override
    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public Employee findOne(UUID employeeId) {

        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found"));

        return employee;

    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    @Transactional
    public Employee creatEmployee(EmployeeCreate employeeCreate) {

        Department department = departmentRepo.findById(employeeCreate.departmentId()).orElseThrow(() -> CustomResponseException.ResourceNotFound("department with id " + employeeCreate.departmentId() + " not found"));

        String token = UUID.randomUUID().toString();

        Employee employee = new Employee();

        employee.setIsVerified(false);
        employee.setAccountCreationToken(token);

        employee.setFirstName(employeeCreate.firstName());

        employee.setLastName(employeeCreate.lastName());

        employee.setPosition(employeeCreate.position());

        employee.setHireDate(employeeCreate.hireDate());

        employee.setPhoneNumber(employeeCreate.phoneNumber());

        employee.setEmail(employeeCreate.email());

        employee.setRole(employeeCreate.role() == null ? Role.EMPLOYEE : employeeCreate.role());

        employee.setDepartment(department);

        employeeRepo.save(employee);

        emailService.sendAccountEmail(employee.getEmail(), token);

        return employee;
    }

    @Override
    public void deleteEmployee(UUID employeeId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found"));

        employeeRepo.delete(employee);
    }

    @Override
    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
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
