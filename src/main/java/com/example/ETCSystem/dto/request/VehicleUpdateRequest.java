package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateRequest {
    private String brand;
    private String model;
    private String color;
    private VehicleType vehicleType;
}
