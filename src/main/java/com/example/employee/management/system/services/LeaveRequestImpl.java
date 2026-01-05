package com.example.employee.management.system.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.employee.management.system.abstracts.LeaveRequestService;
import com.example.employee.management.system.dtos.LeaveRequestCreate;
import com.example.employee.management.system.entities.LeaveRequest;
import com.example.employee.management.system.repositories.EmployeeRepo;
import com.example.employee.management.system.repositories.LeaveRequestRepo;
import com.example.employee.management.system.shared.CustomResponseException;

@Service
public class LeaveRequestImpl implements LeaveRequestService {

    private LeaveRequestRepo leaveRequestRepo;
    private EmployeeRepo employeeRepo;

    public LeaveRequestImpl(LeaveRequestRepo leaveRequestRepo, EmployeeRepo employeeRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public LeaveRequest createOne(LeaveRequestCreate leaveRequestCreate, UUID employeeId) {

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setStartDate(leaveRequestCreate.startDate());
        leaveRequest.setEndDate(leaveRequestCreate.endDate());
        leaveRequest.setReason(leaveRequestCreate.reason());
        leaveRequest.setStatus("PENDING");
        leaveRequest.setEmployee(employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found")));

        return leaveRequestRepo.save(leaveRequest);
    }

    @Override
    @PreAuthorize("@securityUtils.isOwner(#employeeId)")

    public List<LeaveRequest> findAllByEmployeeId(UUID employeeId) {

        employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("employee with id " + employeeId + " not found"));

        return leaveRequestRepo.findAllByEmployeeId(employeeId);
    }

}
