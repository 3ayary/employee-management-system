package com.example.employee.management.system.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.management.system.entities.UserAccount;

public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {
    
    Optional<UserAccount> findOneByUserName(String userName);
}
