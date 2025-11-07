package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.enums.VehicleType;

public class VehicleResponse {
    private String plateNumber;
    private String tagUid;
    private TagStatus tagStatus;
    private VehicleType vehicleType;
}
