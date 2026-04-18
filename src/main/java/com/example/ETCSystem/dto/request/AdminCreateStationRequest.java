package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 6, max = 255, message = "NAME_LENGTH_INVALID")
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
