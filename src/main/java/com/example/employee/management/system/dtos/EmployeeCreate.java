package com.example.employee.management.system.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeCreate(
        @NotNull(message = "first name is requierd")
        @Size(min = 2, max = 50)
        String firstName,
        @NotNull(message = "last name is requierd")
        @Size(min = 2, max = 50)
        String lastName,
        @Email
        @NotNull(message = "email is requierd")
        String email,
        @Pattern(
                regexp = "^(\\+?20)?(10|11|12|15)[0-9]{8}$",
                message = "Invalid phone number"
        )
        @NotNull(message = "phone number is requierd")
        String phoneNumber,
        @NotNull(message = "hire date is requierd")
        @PastOrPresent(message = "hire date cannot be in the future")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate hireDate,
        
        @NotNull(message = "Position is requierd")
        @Size(min = 2, max = 50)
        String position
        ) {

}
