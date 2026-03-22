package com.example.ETCSystem.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateStationRequest {

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String status;

}
