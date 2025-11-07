package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.TagStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRfidTagStatusRequest {

    @NotNull(message = "STATUS_REQUIRED")
    private TagStatus status;
}
