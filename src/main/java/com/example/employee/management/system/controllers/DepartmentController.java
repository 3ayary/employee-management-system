package com.example.employee.management.system.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.management.system.abstracts.DepartmentService;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.employee.management.system.entities.Department;
import com.example.employee.management.system.shared.GlobalResponse;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Department>>> findAll() {
        List<Department> departments = departmentService.findAll();

        return new ResponseEntity<>(new GlobalResponse<>(departments), HttpStatus.OK);
    }

}
