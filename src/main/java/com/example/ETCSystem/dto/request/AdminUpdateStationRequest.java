package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateStationRequest {

    @Size(min = 6, max = 255, message = "NAME_LENGTH_INVALID")
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String status;

}
