package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.request.VehicleCreateRequest;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "user.fullName", target = "ownerName")
    VehicleResponse toVehicleResponse(Vehicle vehicle);

    List<VehicleResponse> toVehicleResponseList(List<Vehicle> vehicles);

    Vehicle toVehicle(VehicleCreateRequest request);
}
