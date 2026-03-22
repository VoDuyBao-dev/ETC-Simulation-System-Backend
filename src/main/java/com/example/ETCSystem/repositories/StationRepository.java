package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.enums.StationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    boolean existsByCode(String code);

    boolean existsByName(String name);

    boolean existsByAddress(String address);

    boolean existsByLatitudeAndLongitudeAndIdNot(Double latitude, Double longitude, Long id);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByAddressAndIdNot(String address, Long id);

    long countByStatus(StationStatus status);

    Optional<Station> findByCode(String stationCode);

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);

    Station findByIdAndIsDelete(Long id, Integer isDelete);

    List<Station> findAllByIsDelete(Integer isDelete);

    long countByIsDelete(Integer isDelete);

    long countByStatusAndIsDelete(StationStatus status, Integer isDelete);

}
