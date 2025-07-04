package com.agriconnect.repositories;

import com.agriconnect.models.FarmerApplication;
import com.agriconnect.util.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FarmerApplicationRepository extends JpaRepository<FarmerApplication,Long> {
    Optional<FarmerApplication> findByUserId(Long userId);
    List<FarmerApplication> findByStatus(ApplicationStatus status);
    List<FarmerApplication> findByStatusOrderByAppliedAtDesc(ApplicationStatus status);
}
