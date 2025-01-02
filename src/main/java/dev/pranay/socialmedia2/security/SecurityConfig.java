package dev.pranay.socialmedia2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())  // Enable CORS configuration
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));  // Allow requests from Angular app
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Allowed HTTP methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));  // Allowed headers
        configuration.setAllowCredentials(true);  // Allow cookies or authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply CORS settings to all endpoints
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Password encoder bean
    }
}
