package com.example.employee.management.system.controllers;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.shared.CustomResponseException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<ArrayList<Employee>> getAllEmployees() {

        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }   

        return new ResponseEntity<ArrayList<Employee>>(employees, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable UUID employeeId) {

        Employee employee = employees.stream().filter(emp -> emp.getId().equals(employeeId)).findFirst().orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found"));

        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> creatEmployee(@RequestBody @Valid Employee employee) {
        employee.setId(UUID.randomUUID());
        employee.setDepartmentId(UUID.randomUUID());
        employees.add(employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID employeeId) {

        Employee employee = employees.stream().filter(emp -> emp.getId().equals(employeeId)).findFirst().orElseThrow(() -> CustomResponseException.ResourceNotFound(("employee with id " + employeeId + " not found")));

        employees.remove(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateOne(@PathVariable UUID employeeId, @RequestBody @Valid Employee employee) {

        Employee existingEmployee = employees.stream().filter(emp -> emp.getId().equals(employeeId)).findFirst().orElseThrow(() -> CustomResponseException.ResourceNotFound(("employee with id " + employeeId + " not found")));


        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhoneNumber(employee.getPhoneNumber());
        existingEmployee.setHireDate(employee.getHireDate());
        existingEmployee.setPosition(employee.getPosition());

        return new ResponseEntity<>(existingEmployee, HttpStatus.OK);
    }

}
