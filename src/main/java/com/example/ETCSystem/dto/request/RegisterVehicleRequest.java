package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.VehicleType;
import lombok.*;

@Getter
@Setter
public class RegisterVehicleRequest {
    private String plateNumber;
    private VehicleType vehicleType;
    // private String brand;
    // private String model;
    // private String color;
}
