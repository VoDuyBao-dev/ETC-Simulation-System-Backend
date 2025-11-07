package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.RfidTag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface RfidTagRepository extends JpaRepository<RfidTag, Long> {
    Optional<RfidTag> findByVehicleId(Long vehicleId);
}
