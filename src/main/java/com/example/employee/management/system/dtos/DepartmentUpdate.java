package com.example.employee.management.system.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentUpdate(
        @NotNull
        @Size(min = 2, max = 20)
        String name
        ) {}
