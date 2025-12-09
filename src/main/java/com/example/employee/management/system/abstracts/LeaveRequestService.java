package com.example.employee.management.system.abstracts;

import java.util.List;
import java.util.UUID;

import com.example.employee.management.system.dtos.LeaveRequestCreate;
import com.example.employee.management.system.entities.LeaveRequest;

public interface LeaveRequestService {

    LeaveRequest createOne(LeaveRequestCreate leaveRequestCreate, UUID employeeId);

    List<LeaveRequest> findAllByEmployeeId(UUID employeeId);

}
