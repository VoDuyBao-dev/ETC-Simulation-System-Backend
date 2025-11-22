package com.example.ETCSystem.repositories;

import java.util.List;
import java.util.Optional;

import com.example.ETCSystem.entities.Vehicle;

import org.springframework.data.jpa.repository.EntityGraph;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUserUserId(Long userId);

    // Page<Vehicle> findByUserUserId(Long userId, Pageable pageable);
    @EntityGraph(attributePaths = "rfidTags")
    Optional<Vehicle> findById(Long id);

}
