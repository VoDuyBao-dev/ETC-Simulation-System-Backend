package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.StationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStationResponse {
    private Long id;
    private String code;
    private String name;
    private String address;       // dùng địa chỉ thay vì cityName
    private StationStatus status;
    private Double latitude;
    private Double longitude;
}
