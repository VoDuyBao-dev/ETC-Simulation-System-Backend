package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRfidReaderRequest {

    @NotBlank(message = "Reader UID cannot be blank")
    private String readerUid;

    private String description;

    @NotNull(message = "Station ID cannot be null")
    private Long stationId;
}
