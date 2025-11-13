package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.AdminVehicleResponse;
import com.example.ETCSystem.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminVehicleMapper {
    @Mapping(target = "FullName", source = "vehicle.user.fullname")
    @Mapping(target = "Email", source = "vehicle.user.email")
    @Mapping(target = "rfidUid", source = "vehicle.rfidTag.tagUid")
    AdminVehicleResponse toAdminVehicleResponse(Vehicle vehicle);
}
