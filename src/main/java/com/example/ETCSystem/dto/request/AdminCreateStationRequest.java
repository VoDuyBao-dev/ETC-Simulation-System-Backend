package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.StationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreateStationRequest {

    // @NotBlank(message = "CODE_REQUIRED")
    // private String code;

    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    @NotBlank(message = "ADDRESS_REQUIRED")
    private String address;

    @NotNull(message = "LATITUDE_REQUIRED")
    private Double latitude;

    @NotNull(message = "LONGITUDE_REQUIRED")
    private Double longitude;
    
    // @NotNull(message = "STATION_REQUIED")
    // private StationStatus status;
}
