package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterVehicleRequest {

    @NotNull(message = "PLATE_NUMBER_REQUIRED")
    @Pattern(regexp = "^[0-9]{2}[A-Z]-[0-9]{3,5}(\\.[0-9]{2})?$", message = "INVALID_PLATE_FORMAT")
    private String plateNumber;

    @NotNull(message = "VEHICLE_TYPE_REQUIRED")
    private VehicleType vehicleType;

    // private String brand;
    // private String model;
    // private String color;
}
