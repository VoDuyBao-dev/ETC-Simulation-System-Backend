package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Vehicle;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminVehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByOrderByIdAsc();

    Vehicle findByIdAndIsDelete(Long id, Integer isDelete);
    Page<Vehicle> findAllByIsDelete(Integer isDelete, Pageable pageable);
}
