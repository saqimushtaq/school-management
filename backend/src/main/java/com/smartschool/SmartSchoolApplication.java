package com.smartschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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
}
