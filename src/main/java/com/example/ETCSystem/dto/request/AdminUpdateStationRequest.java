package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateStationRequest {

    @NotNull(message = "STATION_REQUIED")
    private Long id;

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String status;
    
}
