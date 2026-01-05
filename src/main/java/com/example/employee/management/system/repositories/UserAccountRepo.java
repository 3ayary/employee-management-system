package com.example.employee.management.system.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.employee.management.system.entities.UserAccount;

public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findOneByUserName(String userName);

     @Query("""
     SELECT COUNT(u) > 0 FROM UserAccount u
     WHERE u.userName = :userName AND u.employee.id = :employeeId
             """ )
   public boolean isOwner(@Param("userName") String userName, @Param("employeeId") UUID employeeId);

}
