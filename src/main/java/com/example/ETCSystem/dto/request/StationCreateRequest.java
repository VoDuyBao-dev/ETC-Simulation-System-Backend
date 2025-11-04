package com.example.ETCSystem.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationCreateRequest {
    private String name;
    private String address;
    private String code;
    private Double latitude;
    private Double longitude;
    private String status;
}
