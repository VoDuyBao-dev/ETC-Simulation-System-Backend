package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.enums.StationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {
    boolean existsByCode(String code);
    List<Station> findAllByOrderByCreatedAtDesc();
    List<Station> findByStatus(StationStatus status);
    
}
