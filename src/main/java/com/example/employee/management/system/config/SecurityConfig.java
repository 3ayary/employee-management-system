package com.example.employee.management.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.employee.management.system.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public JwtAuthFilter jwtAuthFilter;
    public UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthFilter jwtAuthFilter) {

        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())   
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/auth/signup",
                            "/auth/signin",
                            "/auth/forgot-password/{username}",
                            "/auth/reset-password"
                    ).permitAll() 
                            .requestMatchers(HttpMethod.GET, "/employees").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/employees/{employeeId}").hasAnyRole("ADMIN", "EMPLOYEE")
                            .requestMatchers(HttpMethod.POST, "/employees").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/employees/{employeeId}").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/employees/{employeeId}").hasAnyRole("ADMIN", "EMPLOYEE")
                            .requestMatchers(HttpMethod.POST, "/employees/{employeeId}/leave-requests").hasAnyRole("ADMIN", "EMPLOYEE")
                            .requestMatchers(HttpMethod.GET, "/employees/{employeeId}/leave-requests").hasAnyRole("ADMIN", "EMPLOYEE")
                            .anyRequest()
                            .authenticated();
                }).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
