package com.agriconnect.repositories;

import com.agriconnect.models.User;
import com.agriconnect.util.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> findByUserType(UserType userType);
    List<User> findByUserTypeAndIsApproved(UserType userType, boolean isApproved);

}
