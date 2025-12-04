package com.example.employee.management.system.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.management.system.abstracts.EmployeeService;
import com.example.employee.management.system.dtos.EmployeeCreate;
import com.example.employee.management.system.dtos.EmployeeUpdate;
import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.shared.GlobalResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Employee>>> getAllEmployees() {

        return new ResponseEntity<>(new GlobalResponse<>(employeeService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable UUID employeeId) {

        return new ResponseEntity<Employee>(employeeService.findOne(employeeId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> creatEmployee(@RequestBody @Valid EmployeeCreate employee) {

        return new ResponseEntity<Employee>(employeeService.creatEmployee(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID employeeId) {

        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateOne(@PathVariable UUID employeeId, @RequestBody @Valid EmployeeUpdate employee) {

        return new ResponseEntity<>(employeeService.updateOne(employeeId, employee), HttpStatus.OK);
    }

}
