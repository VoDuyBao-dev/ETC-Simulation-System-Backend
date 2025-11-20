package com.example.ETCSystem.dto.request;

// import com.example.ETCSystem.enums.VehicleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateVehicleRequest {

    // @NotNull(message = "VEHICLE_ID_REQUIRED")
    // private Long vehicleId;

    @NotNull(message = "STATUS_REQUIRED")
    private String status;
}
