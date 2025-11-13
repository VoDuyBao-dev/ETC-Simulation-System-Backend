package com.example.ETCSystem.dto.request;

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
