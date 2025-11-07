package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.TagStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRfidTagResponse {
    private Long id;
    private String tagUid;
    private String vehiclePlate;
    private LocalDateTime issuedAt;
    private TagStatus status;
}
