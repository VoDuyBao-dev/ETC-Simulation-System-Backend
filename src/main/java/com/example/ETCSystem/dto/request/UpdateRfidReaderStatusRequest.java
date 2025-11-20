package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRfidReaderStatusRequest {

    @NotNull(message = "IS_ACTIVATE_REQUIRED")
    private Boolean isActive;
}
