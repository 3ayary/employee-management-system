package com.example.employee.management.system.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record LeaveRequestCreate(
        
        @FutureOrPresent
        @NotNull
        LocalDate startDate,

        @Future
        @NotNull
        LocalDate endDate,

        String reason
) {

}
