package com.example.employee.management.system.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "password_reset_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  PasswordResetToken {

    @Id
    @GeneratedValue(generator= "UUID")
    @UuidGenerator
    private UUID id ;

    @Column(name="token" , unique=true , nullable=false)
    private String token ;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable=false,unique=true)
    private UserAccount user; 

    @Column(nullable=false)
    private LocalDateTime expiryDate;

}
