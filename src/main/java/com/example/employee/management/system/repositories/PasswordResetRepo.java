package com.example.employee.management.system.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.management.system.entities.PasswordResetToken;


public interface PasswordResetRepo extends JpaRepository<PasswordResetToken, UUID>{
    Optional<PasswordResetToken> findOneByToken(String token); 
     

    Optional<PasswordResetToken> findOneByUser_Id(UUID userId);

}
