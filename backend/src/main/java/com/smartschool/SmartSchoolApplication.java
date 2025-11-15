package com.smartschool;

import com.smartschool.auth.entity.User;
import com.smartschool.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main Spring Boot Application Class for SmartSchool
 * School Management System
 */
@SpringBootApplication
@EnableJpaAuditing
public class SmartSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSchoolApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            var user = userRepository.findByUsername("admin");
            if (user.isEmpty()) {
                var adminUser = com.smartschool.auth.entity.User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .isEnabled(true)
                        .role(User.UserRole.ADMIN)
                        .isEnabled(true)
                        .build();
                userRepository.save(adminUser);
                System.out.println("Admin user created with username: admin and password: admin123");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
