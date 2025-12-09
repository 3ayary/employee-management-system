package com.example.employee.management.system.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.management.system.entities.LeaveRequest;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findAllByEmployeeId(UUID employeeId);
}
