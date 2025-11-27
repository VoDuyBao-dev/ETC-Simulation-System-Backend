package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.RfidTagResponse;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", source = "vehicle.id")
    @Mapping(target = "plateNumber", source = "vehicle.plateNumber")
    @Mapping(target = "vehicleType", source = "vehicle.vehicleType")
    @Mapping(target = "vehicleStatus", source = "vehicle.status")
    @Mapping(target = "tagUid", source = "vehicle", qualifiedByName = "mapTagUid")
    @Mapping(target = "tagStatus", source = "vehicle", qualifiedByName = "mapTagStatus")
    VehicleResponse toVehicleResponse(Vehicle vehicle);

    // Lấy UID thẻ ACTIVE
    @Named("mapTagUid")
    default String mapActiveTagUid(Vehicle vehicle) {
        if (vehicle.getRfidTags() == null) return null;
        return vehicle.getRfidTags().stream()
                // .filter(tag -> tag.getStatus().name().equals("ACTIVE"))
                .map(RfidTag::getTagUid)
                .findFirst()
                .orElse(null);
    }

    // Lấy Status của thẻ ACTIVE
    @Named("mapTagStatus")
    default String mapActiveTagStatus(Vehicle vehicle) {
        if (vehicle.getRfidTags() == null) return null;
        return vehicle.getRfidTags().stream()
                // .filter(tag -> tag.getStatus().name().equals("ACTIVE"))
                .map(tag -> tag.getStatus().name())
                .findFirst()
                .orElse(null);
    }

    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "status", expression = "java(rfidTag.getStatus().name())")
    RfidTagResponse toRfidTagResponse(RfidTag rfidTag);
}
