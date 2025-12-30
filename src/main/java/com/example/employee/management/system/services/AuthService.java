package com.example.employee.management.system.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee.management.system.config.JwtHelper;
import com.example.employee.management.system.dtos.SigninRequest;
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
    private AuthenticationManager authManager;
    private JwtHelper jwtHelper;

    public AuthService(UserAccountRepo userAccountRepo, EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder, AuthenticationManager authManager, JwtHelper jwtHelper) {
        this.userAccountRepo = userAccountRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtHelper = jwtHelper;
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

    public String signin(SigninRequest signinRequest) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.userName(), signinRequest.password())
        );

        UserAccount user = userAccountRepo.findOneByUserName(signinRequest.userName()).orElseThrow(() -> CustomResponseException.BadCredintials());

        Map<String, Object> customClaims = new HashMap<>();

        customClaims.put("userId", user.getId());

        return jwtHelper.generateToken(customClaims, user);
    }

}
