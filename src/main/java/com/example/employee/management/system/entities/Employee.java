package com.example.employee.management.system.entities;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {

    private UUID id;
    @NotNull(message = "first name is requierd")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotNull(message = "last name is requierd")
    @Size(min = 2, max = 50)
    private String lastName;
    @Email
    private String email;
    @Pattern(
            regexp = "^(\\+?20)?(10|11|12|15)[0-9]{8}$",
            message = "Invalid phone number"
    )
    private String phoneNumber;

    private String hireDate;
    @NotNull(message = "last name is requierd")
    @Size(min = 2, max = 50)
    private String position;
    private UUID departmentId;

}
