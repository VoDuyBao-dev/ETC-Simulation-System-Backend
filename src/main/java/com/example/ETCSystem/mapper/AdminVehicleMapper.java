package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.AdminVehicleResponse;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AdminVehicleMapper {

    @Mapping(target = "FullName", source = "vehicle.user.fullname")
    @Mapping(target = "Email", source = "vehicle.user.email")
    @Mapping(target = "rfidUid", source = "vehicle", qualifiedByName = "mapActiveTagUid")
    AdminVehicleResponse toAdminVehicleResponse(Vehicle vehicle);

    // Lấy duy nhất thẻ ACTIVE
    @Named("mapActiveTagUid")
    default String mapActiveTagUid(Vehicle vehicle) {
        if (vehicle.getRfidTags() == null) return null;
        return vehicle.getRfidTags().stream()
                .filter(tag -> tag.getStatus().name().equals("ACTIVE"))
                .map(RfidTag::getTagUid)
                .findFirst()
                .orElse(null);
    }
}
