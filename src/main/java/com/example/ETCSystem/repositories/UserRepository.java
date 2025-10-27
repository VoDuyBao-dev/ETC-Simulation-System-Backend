package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEnabledFalseAndActivationExpiryTimeBefore(LocalDateTime expiryTime);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
