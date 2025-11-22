package com.example.ETCSystem.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RfidTagResponse {
    private Long id;
    private String tagUid;
    private String status;
    private Long vehicleId;
    private String issuedAt;
}
