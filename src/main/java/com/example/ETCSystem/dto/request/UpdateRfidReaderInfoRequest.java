package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRfidReaderInfoRequest {

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Long stationId; // cho phép đổi sang trạm khác (nếu cần)
}
