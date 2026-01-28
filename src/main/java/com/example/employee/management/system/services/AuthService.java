package com.example.employee.management.system.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee.management.system.config.JwtHelper;
import com.example.employee.management.system.dtos.ResetPasswordRequest;
import com.example.employee.management.system.dtos.SigninRequest;
import com.example.employee.management.system.dtos.SignupRequest;
import com.example.employee.management.system.entities.Employee;
import com.example.employee.management.system.entities.PasswordResetToken;
import com.example.employee.management.system.entities.UserAccount;
import com.example.employee.management.system.repositories.EmployeeRepo;
import com.example.employee.management.system.repositories.PasswordResetRepo;
import com.example.employee.management.system.repositories.UserAccountRepo;
import com.example.employee.management.system.shared.CustomResponseException;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private UserAccountRepo userAccountRepo;
    private EmployeeRepo employeeRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authManager;
    private JwtHelper jwtHelper;
    private PasswordResetRepo passwordResetRepo;
    private EmailService emailService;

    public AuthService(UserAccountRepo userAccountRepo, EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder, AuthenticationManager authManager, JwtHelper jwtHelper, PasswordResetRepo passwordResetRepo, EmailService emailService) {
        this.userAccountRepo = userAccountRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtHelper = jwtHelper;
        this.passwordResetRepo = passwordResetRepo;
        this.emailService = emailService;
    }

    public void signup(SignupRequest signupRequest, String token) {

        Employee employee = employeeRepo.findOneByAccountCreationToken(token)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("Invaild token!"));

        if (employee.getIsVerified()) {
            throw CustomResponseException.BadRequest("Account already created!");
        }

        UserAccount account = new UserAccount();
        account.setUserName(signupRequest.userName());
        account.setPassword(passwordEncoder.encode(signupRequest.password()));
        account.setEmployee(employee);

        userAccountRepo.save(account);

        employee.setIsVerified(true);
        employee.setAccountCreationToken(null);
        employeeRepo.save(employee);
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

  
    @Transactional
    public void initiatePasswordReset(String username) {
        try {
            UserAccount user = userAccountRepo.findOneByUserName(username)
                    .orElseThrow(() -> CustomResponseException.ResourceNotFound("account not found!"));

            if (passwordResetRepo.findOneByUser_Id(user.getId()).isPresent()) {
                throw CustomResponseException.BadRequest("!");
            }

            String token = UUID.randomUUID().toString();
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setExpiryDate(expiry);
            resetToken.setUser(user);
            passwordResetRepo.save(resetToken);
            emailService.sendPasswordResetEmail(user.getEmployee().getEmail(), token);

        } catch (Exception e) {
            throw CustomResponseException.BadRequest("Reset password failed!");
        }

    }

    @Transactional
    public void passwordReset(ResetPasswordRequest resetPasswordRequest) {

        PasswordResetToken passwordResetToken = passwordResetRepo.findOneByToken(resetPasswordRequest.token()).orElseThrow(
                () -> CustomResponseException.BadRequest("invaild token!")
        );

        boolean isTokenExpired = passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now());
        if (isTokenExpired) {
            passwordResetRepo.delete(passwordResetToken);
            throw CustomResponseException.BadRequest("token expired!");
        }

        UserAccount user = passwordResetToken.getUser();

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        userAccountRepo.save(user);
        passwordResetRepo.delete(passwordResetToken);

    }

}
