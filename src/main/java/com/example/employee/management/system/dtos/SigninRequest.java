package com.example.employee.management.system.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SigninRequest(
        @NotNull(message = "user name is requierd !")
        @Size(min = 2, max = 20, message = "min characters is 2 and max is 20 ")
        String userName,
        
        @NotNull(message = "password is requierd !")
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_+=\\-])[A-Za-z\\d@$!%*?&#^()_+=\\-]{8,}$")
        String password
        ) {

}
