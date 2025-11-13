package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    @Mapping(target = "tagStatus", source = "vehicle.rfidTag.status")
    @Mapping(target = "tagUid", source = "vehicle.rfidTag.tagUid")
    VehicleResponse toVehicleResponse(Vehicle vehicle);
}