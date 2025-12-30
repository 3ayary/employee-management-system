package com.example.employee.management.system.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.employee.management.system.entities.UserAccount;
import com.example.employee.management.system.repositories.UserAccountRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserAccountRepo userAccountRepo;

    public UserDetailsServiceImpl(UserAccountRepo userAccountRepo) {
        this.userAccountRepo = userAccountRepo;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> account = userAccountRepo.findOneByUserName(username);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);

        }
        UserAccount user = account.get();

        return User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
    }

}
