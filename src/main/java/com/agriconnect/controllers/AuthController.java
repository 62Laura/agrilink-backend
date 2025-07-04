package com.agriconnect.controllers;

import com.agriconnect.dto.*;
import com.agriconnect.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed", e);
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @PostMapping("/signup/citizen")
    public ResponseEntity<AuthResponse> citizenSignup(@Valid @RequestBody CitizenSignUpRequest request) {
        try {
            AuthResponse response = authService.citizenSignup(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Citizen signup failed", e);
            return ResponseEntity.badRequest()
                    .body(AuthResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @PostMapping("/apply/farmer")
    public ResponseEntity<ApiResponse> farmerApplication(@Valid @RequestBody FarmerApplicationRequest request) {
        try {
            ApiResponse response = authService.farmerApplication(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Farmer application failed", e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

}
