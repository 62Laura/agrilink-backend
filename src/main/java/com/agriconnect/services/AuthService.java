package com.agriconnect.services;

import com.agriconnect.dto.*;
import com.agriconnect.models.FarmerApplication;
import com.agriconnect.models.User;
import com.agriconnect.repositories.FarmerApplicationRepository;
import com.agriconnect.repositories.UserRepository;
import com.agriconnect.util.ApplicationStatus;
import com.agriconnect.util.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final FarmerApplicationRepository farmerApplicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.getUserType() == UserType.FARMER && !user.isApproved()) {
                return AuthResponse.builder()
                        .message("Your farmer application is still pending approval")
                        .build();
            }

            String token = jwtService.generateToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .message("Login successful")
                    .userType(user.getUserType())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .isApproved(user.isApproved())
                    .build();

        } catch (Exception e) {
            log.error("Login failed for email: {}", request.getEmail(), e);
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Transactional
    public AuthResponse citizenSignup(CitizenSignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .fullName(request.getFullName())
                .location(request.getLocation())
                .userType(UserType.CITIZEN)
                .isApproved(true)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        log.info("Citizen registered successfully: {}", savedUser.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("Citizen registration successful")
                .userType(savedUser.getUserType())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .isApproved(true)
                .build();
    }

    @Transactional
    public ApiResponse farmerApplication(FarmerApplicationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }


        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .fullName(request.getFullName())
                .location(request.getLocation())
                .userType(UserType.FARMER)
                .isApproved(false)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        FarmerApplication application = FarmerApplication.builder()
                .user(savedUser)
                .farmName(request.getFarmName())
                .farmSize(request.getFarmSize())
                .cropTypes(request.getCropTypes())
                .yearsOfExperience(request.getYearsOfExperience())
                .motivation(request.getMotivation())
                .status(ApplicationStatus.PENDING)
                .build();

        farmerApplicationRepository.save(application);

        log.info("Farmer application submitted: {}", savedUser.getEmail());

        return ApiResponse.success("Farmer application submitted successfully. You will receive an email once approved.", application.getId());
    }
}

