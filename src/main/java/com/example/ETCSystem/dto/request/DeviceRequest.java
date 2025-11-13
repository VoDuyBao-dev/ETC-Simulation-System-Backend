package com.example.ETCSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest {
    private String rfidTagCode;
    private String stationCode ;
    private String readerUid;
}
