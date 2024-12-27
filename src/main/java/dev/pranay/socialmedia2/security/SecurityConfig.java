package dev.pranay.socialmedia2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Explicitly allow access to /signup without authentication
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup").permitAll() // No authentication required for signup
                        .anyRequest().authenticated()  // Other requests require authentication
                )
                .formLogin(Customizer.withDefaults())  // Form login configuration
                .httpBasic(Customizer.withDefaults())  // Basic HTTP authentication (optional)
                .csrf(csrf -> csrf.disable())  // CSRF protection (adjust if needed)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Password encoder bean
    }
}
