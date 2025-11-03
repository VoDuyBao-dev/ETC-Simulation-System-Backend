package com.example.ETCSystem.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStatusRequest {
    private String status; // "ACTIVE" | "BLOCKED" | "PENDING"
}
