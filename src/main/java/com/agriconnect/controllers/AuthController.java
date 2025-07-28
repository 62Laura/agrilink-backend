package com.agriconnect.controllers;

import com.agriconnect.dto.*;
import com.agriconnect.models.MembershipApplication;
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

    /**
     * Endpoint for user login.
     * Accepts login credentials (email, password) and returns an AuthResponse if successful.
     */
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

    /**
     * Endpoint for citizen sign-up.
     * Registers a new user with role 'CITIZEN' and returns an AuthResponse.
     */
    @PostMapping("public/signup/citizen")
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

    /**
     * Endpoint for farmers to apply for membership.
     * Saves the application for admin review.
     */
    @PostMapping("public/apply/farmer")
    public ResponseEntity<ApiResponse> farmerApplication(@Valid @RequestBody MembershipApplicationRequest request) {
        try {
            ApiResponse response = authService.applyForMembership(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Farmer application failed", e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Admin-only endpoint to retrieve all pending farmer membership applications.
     */
    @GetMapping("/admin/membership-applications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MembershipApplication>> listPendingApplications() {
        List<MembershipApplication> applications = authService.getAllPendingApplications();
        return ResponseEntity.ok(applications);
    }

    /**
     * Admin-only endpoint to approve a farmer's membership application by ID.
     */
    @PostMapping("/admin/approve-membership")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> approveMembership(@RequestParam("applicationId") Long applicationId) {
        ApiResponse response = authService.approveMembership(applicationId);
        return ResponseEntity.ok(response);
    }
}
