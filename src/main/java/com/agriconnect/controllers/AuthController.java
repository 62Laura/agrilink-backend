package com.agriconnect.controllers;

import com.agriconnect.dto.*;
import com.agriconnect.models.MembershipApplication;
import com.agriconnect.repositories.UserRepository;
import com.agriconnect.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed", e);
            return ResponseEntity.badRequest().body(
                    AuthResponse.builder().message(e.getMessage()).build()
            );
        }
    }

    @PostMapping("/signup/citizen")
    public ResponseEntity<AuthResponse> citizenSignup(@Valid @RequestBody CitizenSignUpRequest request) {
        try {
            AuthResponse response = authService.citizenSignup(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Citizen signup failed", e);
            return ResponseEntity.badRequest().body(
                    AuthResponse.builder().message(e.getMessage()).build()
            );
        }
    }

    @PostMapping("/apply/farmer")
    public ResponseEntity<ApiResponse> farmerApplication(@Valid @RequestBody MembershipApplicationRequest request) {
        try {
            ApiResponse response = authService.applyForMembership(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Farmer application failed", e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/admin/membership-applications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MembershipApplication>> listPendingApplications() {
        List<MembershipApplication> applications = authService.getAllPendingApplications();
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/admin/approve-membership")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> approveMembership(@RequestParam("applicationId") Long applicationId) {
        ApiResponse response = authService.approveMembership(applicationId);
        return ResponseEntity.ok(response);
    }
}

