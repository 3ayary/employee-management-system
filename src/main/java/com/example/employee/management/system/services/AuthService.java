package com.example.employee.management.system.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee.management.system.dtos.SignupRequest;
import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.entities.UserAccount;
import com.example.employee.management.system.enums.Role;
import com.example.employee.management.system.repositories.EmployeeRepo;
import com.example.employee.management.system.repositories.UserAccountRepo;
import com.example.employee.management.system.shared.CustomResponseException;

@Service
public class AuthService {

    private UserAccountRepo userAccountRepo;
    private EmployeeRepo employeeRepo;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserAccountRepo userAccountRepo, EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder) {
        this.userAccountRepo = userAccountRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest signupRequest) {

        Employee employee = employeeRepo.findById(signupRequest.employeeId())
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("Employee with id: " + signupRequest.employeeId() + " is not found !"));

        Role role = signupRequest.role() != null ? signupRequest.role() : Role.EMPLOYEE;
        UserAccount account = new UserAccount();
        account.setUserName(signupRequest.userName());
        account.setPassword(passwordEncoder.encode(signupRequest.password()));
        account.setRole(role);
        account.setEmployee(employee);

        userAccountRepo.save(account);

    }

  
}
