package com.example.employee.management.system.dtos;


public record ResetPasswordRequest(
    String token,
    String newPassword
) {
    
}
