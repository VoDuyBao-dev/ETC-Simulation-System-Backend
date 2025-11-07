package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.VehicleStatus;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVehicleStatusRequest {
    @NotNull(message = "STATUS_REQUIRED")
    private String status;
}
