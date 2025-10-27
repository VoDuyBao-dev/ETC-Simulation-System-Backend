package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    OtpVerification findTopByEmailOrderByCreatedAtDesc(String email);
}
