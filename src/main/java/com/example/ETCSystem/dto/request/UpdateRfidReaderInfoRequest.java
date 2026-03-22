package com.example.ETCSystem.dto.request;

import lombok.Data;

@Data
public class UpdateRfidReaderInfoRequest {
    private String description;
    private Long stationId; // cho phép đổi sang trạm khác (nếu cần)
}
