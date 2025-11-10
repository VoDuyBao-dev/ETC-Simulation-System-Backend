package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.enums.StationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    boolean existsByCode(String code);
    long countByStatus(StationStatus status);
}
