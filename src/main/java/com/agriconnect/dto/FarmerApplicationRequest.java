package com.agriconnect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FarmerApplicationRequest {
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Farm name is required")
    private String farmName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Farm size is required")
    private String farmSize;

    @NotBlank(message = "Crop types are required")
    private String cropTypes;

    @NotNull(message = "Years of experience is required")
    private Integer yearsOfExperience;

    @NotBlank(message = "Please explain why you want to join AgriConnect")
    private String motivation;
}
