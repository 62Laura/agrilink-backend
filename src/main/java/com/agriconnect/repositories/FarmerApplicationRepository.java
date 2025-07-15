package com.agriconnect.repositories;

import com.agriconnect.models.MembershipApplication;
import com.agriconnect.util.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FarmerApplicationRepository extends JpaRepository<MembershipApplication,Long> {
    Optional<MembershipApplication> findByUserId(Long userId);
    List<MembershipApplication> findByStatus(ApplicationStatus status);
    List<MembershipApplication> findByStatusOrderByAppliedAtDesc(ApplicationStatus status);
}
