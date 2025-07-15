package com.agriconnect.services;

import com.agriconnect.models.User;
import com.agriconnect.repositories.UserRepository;
import com.agriconnect.util.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInitService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdmin();
    }

    private void initializeAdmin() {
        String adminEmail = "c.ishimwe8@alustudent.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("Password@123"))
                    .fullName("AgriLink Admin")
                    .userType(UserType.ADMIN)
                    .isApproved(true)
                    .isActive(true)
                    .build();

            userRepository.save(admin);
            log.info("🔑 Admin user created successfully: {}", adminEmail);
        } else {
            log.info("👤 Admin user already exists: {}", adminEmail);
        }
    }
}
