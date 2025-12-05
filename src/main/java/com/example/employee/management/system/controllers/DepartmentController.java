package com.example.employee.management.system.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.management.system.abstracts.DepartmentService;
import com.example.employee.management.system.dtos.DepartmentCreate;
import com.example.employee.management.system.entities.Department;
import com.example.employee.management.system.shared.GlobalResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

import com.example.employee.management.system.dtos.DepartmentUpdate;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService ;

    DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Department>>> findAll() {
        List<Department> departments = departmentService.findAll();

        return new ResponseEntity<>(new GlobalResponse<>(departments), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse> findOne(@PathVariable UUID departmentId) {

        return new ResponseEntity<>(new GlobalResponse<>(departmentService.findOne(departmentId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse> createDepartment(@RequestBody @Valid DepartmentCreate departmentCreate) {

        return new ResponseEntity<>(new GlobalResponse<>(departmentService.createDepartment(departmentCreate)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartment(@PathVariable UUID departmentId) {

        departmentService.deleteOne(departmentId);

    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse> putMethodName(@PathVariable UUID departmentId, @RequestBody DepartmentUpdate department) {

        return new ResponseEntity<>(new GlobalResponse(departmentService.updateDepartment(departmentId, department)), HttpStatus.OK);
    }

}
