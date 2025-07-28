package com.agriconnect.dto;

import com.agriconnect.util.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Authentication Responses
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private UserType userType;
    private String username;
    private String email;
    private boolean isApproved;
}
