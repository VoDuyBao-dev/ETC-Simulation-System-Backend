package com.example.ETCSystem.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminRfidReaderResponse {
    private Long id;
    private String readerUid;
    private String description;
    private Boolean isActive;
    private LocalDateTime lastHeartbeat;
    private LocalDateTime createdAt;
    private String stationCode;
    private String stationName;
}
