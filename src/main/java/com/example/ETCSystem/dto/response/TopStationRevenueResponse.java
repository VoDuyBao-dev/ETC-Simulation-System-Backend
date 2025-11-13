package com.example.ETCSystem.dto.response;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class TopStationRevenueResponse {
    private Long stationId;
    private String stationCode;   // giả định Station có field code
    private String stationName;   // giả định Station có name
    private long revenue;         // tổng doanh thu tại trạm
}
