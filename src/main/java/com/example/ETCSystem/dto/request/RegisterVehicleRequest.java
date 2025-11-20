package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterVehicleRequest {

    @NotNull(message = "PLATE_NUMBER_REQUIRED")
    private String plateNumber;

    @NotNull(message = "VEHICLE_TYPE_REQUIRED")
    private VehicleType vehicleType;

    // private String brand;
    // private String model;
    // private String color;
}
