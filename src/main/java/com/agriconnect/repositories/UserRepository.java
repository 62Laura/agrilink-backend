package com.agriconnect.repositories;

import com.agriconnect.models.User;
import com.agriconnect.util.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByUserType(UserType userType);
    List<User> findByUserTypeAndIsApproved(UserType userType, boolean isApproved);

}
