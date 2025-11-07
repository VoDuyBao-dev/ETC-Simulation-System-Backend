package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.Vehicle;

public interface VehicleMapper {
    VehicleResponse toVehicleResponse(Vehicle vehicle);
}