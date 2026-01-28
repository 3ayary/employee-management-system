package com.example.employee.management.system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.management.system.dtos.ResetPasswordRequest;
import com.example.employee.management.system.dtos.SigninRequest;
import com.example.employee.management.system.dtos.SignupRequest;
import com.example.employee.management.system.services.AuthService;
import com.example.employee.management.system.shared.GlobalResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<?>> singup(@RequestBody SignupRequest signupRequest, @RequestParam String token) {

        authService.signup(signupRequest, token);
        return new ResponseEntity<>(new GlobalResponse<>("User Created Successfully"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<GlobalResponse<String>> signin(@RequestBody SigninRequest signinRequest) {

        String token = authService.signin(signinRequest);

        return new ResponseEntity<>(new GlobalResponse<>(token), HttpStatus.OK);
    }

    @PostMapping("/forgot-password/{username}")
    public ResponseEntity<GlobalResponse<String>> forgotPassword(@PathVariable String username) {

        authService.initiatePasswordReset(username);
        return new ResponseEntity<>(new GlobalResponse<>("Done!"), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GlobalResponse<String>> forgotPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        authService.passwordReset(resetPasswordRequest);

        return new ResponseEntity<>(new GlobalResponse<>("Done!"), HttpStatus.OK);
    }
}
