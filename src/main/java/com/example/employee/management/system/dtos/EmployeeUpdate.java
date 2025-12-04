package com.example.employee.management.system.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdate(

        @NotNull(message = "first name is requierd")
        @Size(min = 2, max = 50)
        String firstName,

        @NotNull(message = "last name is requierd")
        @Size(min = 2, max = 50)
        String lastName,

        @Pattern(
                regexp = "^(\\+?20)?(10|11|12|15)[0-9]{8}$",
                message = "Invalid phone number"
        )
        @NotNull(message = "phone number is requierd")
        String phoneNumber,

        @NotNull(message = "Position is requierd")
        @Size(min = 2, max = 50)
        String position
) {
    
}
