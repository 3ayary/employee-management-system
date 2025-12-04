package com.example.employee.management.system.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.management.system.entities.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,UUID> {
    
}
