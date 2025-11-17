package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Topup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopupRepository extends JpaRepository<Topup,Long> {
    Optional<Topup> findByReferenceCode(String referenceCode);

}
