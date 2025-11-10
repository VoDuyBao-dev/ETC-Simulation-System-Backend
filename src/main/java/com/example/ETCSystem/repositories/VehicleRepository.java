package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

}
