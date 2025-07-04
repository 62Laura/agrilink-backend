package com.agriconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AgriConnect {
    public static void main(String[] args) {
        SpringApplication.run(AgriConnect.class, args);
        System.out.println("🌱 AgriConnect Rwanda Backend is running!");
    }
}
