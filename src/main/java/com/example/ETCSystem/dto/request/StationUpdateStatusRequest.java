package com.example.ETCSystem.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationUpdateStatusRequest {
    private String status;
}
