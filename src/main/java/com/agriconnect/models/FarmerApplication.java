package com.agriconnect.models;

import com.agriconnect.util.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "farmer_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmerApplication {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String farmName;
    private String farmSize;
    private String cropTypes;
    private Integer yearsOfExperience;

    @Column(columnDefinition = "TEXT")
    private String motivation;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    private String rejectionReason;

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
    }
}
