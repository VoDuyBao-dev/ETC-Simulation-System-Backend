package com.example.ETCSystem.dto.response;

import lombok.*;

// import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class FailedTransactionRow {
    private Long transactionId;
    private String stationCode;      // Mã trạm
    private String plateNumber;      // Biển số xe
    private LocalDateTime occurredAt;      // Thời gian giao dịch
    private String status;           // Lỗi/status
    private String reason;           // tùy chọn: mô tả lỗi nếu có
}
