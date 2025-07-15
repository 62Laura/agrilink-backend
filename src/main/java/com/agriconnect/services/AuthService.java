package com.agriconnect.services;

import com.agriconnect.dto.*;
import com.agriconnect.models.MembershipApplication;
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

import java.time.LocalDateTime;
import java.util.List;

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
    public ApiResponse applyForMembership(MembershipApplicationRequest request) {
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

        user = userRepository.save(user);

        MembershipApplication application = MembershipApplication.builder()
                .user(user)
                .type(request.getType())
                .farmName(request.getFarmName())
                .cropTypes(request.getCropTypes())
                .cooperativeName(request.getCooperativeName())
                .appliedAt(LocalDateTime.now())
                .build();

        farmerApplicationRepository.save(application);

        return ApiResponse.success("Membership application submitted successfully", application.getId());
    }
    public List<MembershipApplication> getAllPendingApplications() {
        return farmerApplicationRepository.findByStatus(ApplicationStatus.PENDING);
    }
    @Transactional
    public ApiResponse approveMembership(Long applicationId) {
        MembershipApplication application = farmerApplicationRepository
                .findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new RuntimeException("Application already processed");
        }
        application.setStatus(ApplicationStatus.APPROVED);
        application.setReviewedAt(LocalDateTime.now());
        User user = application.getUser();
        userRepository.save(user);
       farmerApplicationRepository.save(application);
        return ApiResponse.success("Application approved successfully");
    }


}

