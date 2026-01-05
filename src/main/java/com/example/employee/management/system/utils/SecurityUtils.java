package com.example.employee.management.system.utils;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.employee.management.system.repositories.UserAccountRepo;

@Component
public class SecurityUtils {

    private UserAccountRepo userAccountRepo;

    public SecurityUtils(UserAccountRepo userAccountRepo) {
        this.userAccountRepo = userAccountRepo;
    }

    public boolean isOwner(UUID incomingEmployeeId) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userAccountRepo.isOwner(userDetails.getUsername(), incomingEmployeeId);
    }

}
