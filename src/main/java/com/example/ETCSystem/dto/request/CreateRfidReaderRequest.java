package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRfidReaderRequest {

    @NotBlank(message = "READER_ID_REQUIRED")
    private String readerUid;

    private String description;

    @NotNull(message = "STATION_ID_REQUIRED")
    private Long stationId;
}
