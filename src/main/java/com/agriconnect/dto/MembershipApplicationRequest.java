package com.agriconnect.dto;

import com.agriconnect.util.MembershipType;
import lombok.Data;

@Data
public class MembershipApplicationRequest {
    private String email;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String location;
    private MembershipType type;
    private String farmName;
    private String cropTypes;
    private String cooperativeName;
}
