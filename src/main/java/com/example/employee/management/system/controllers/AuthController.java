package com.example.employee.management.system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<GlobalResponse<?>> singup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return new ResponseEntity<>(new GlobalResponse<>("User Created Successfully"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<GlobalResponse<?>> signin(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return new ResponseEntity<>(new GlobalResponse<>("User Created Successfully"), HttpStatus.OK);
    }
}
