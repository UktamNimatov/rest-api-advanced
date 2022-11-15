package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class RestApiAdvancedApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiAdvancedApplication.class, args);
    }
}
