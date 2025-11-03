package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.StationStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StationResponse {
    private Long id;
    private String name;
    private String address;
    private String code;
    private Double latitude;
    private Double longitude;
    private StationStatus status;
    private LocalDateTime createdAt;
}
