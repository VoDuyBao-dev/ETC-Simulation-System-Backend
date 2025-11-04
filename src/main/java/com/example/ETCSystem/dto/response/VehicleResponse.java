package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponse {
    private Long id;
    private String plateNumber;
    private VehicleType vehicleType;
    private String brand;
    private String model;
    private String color;
    private String ownerName;
}
