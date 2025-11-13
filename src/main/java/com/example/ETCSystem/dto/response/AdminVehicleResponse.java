package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.VehicleStatus;
import com.example.ETCSystem.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminVehicleResponse {
    private Long id;
    private String plateNumber;
    private VehicleType vehicleType;
    private String FullName;
    private String Email;
    private String rfidUid;
    private VehicleStatus status;
}
