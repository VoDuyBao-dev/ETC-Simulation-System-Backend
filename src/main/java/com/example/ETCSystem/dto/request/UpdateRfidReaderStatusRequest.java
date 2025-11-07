package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRfidReaderStatusRequest {

    @NotNull(message = "isActive must not be null")
    private Boolean isActive;
}
