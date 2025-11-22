package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.enums.TagStatus;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface RfidTagRepository extends JpaRepository<RfidTag, Long> {
    Optional<RfidTag> findByVehicleId(Long vehicleId);

    Optional<RfidTag> findByTagUid(String tagUid);

    @Query("SELECT t FROM RfidTag t WHERE t.vehicle.id=:vehicleId AND t.status=:status")
    Optional<RfidTag> findByVehicleIdAndStatus(Long vehicleId, TagStatus status);

    List<RfidTag> findAllByVehicleId(Long vehicleId);

}
