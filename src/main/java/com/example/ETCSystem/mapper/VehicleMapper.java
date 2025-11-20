package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", source = "vehicle.id")
    @Mapping(target = "tagStatus", source = "vehicle.rfidTag.status")
    @Mapping(target = "tagUid", source = "vehicle.rfidTag.tagUid")
    @Mapping(target = "vehicleStatus", source = "vehicle.status")
    @Mapping(target = "plateNumber", source = "vehicle.plateNumber")
    @Mapping(target = "vehicleType", source = "vehicle.vehicleType")
    VehicleResponse toVehicleResponse(Vehicle vehicle);
}
