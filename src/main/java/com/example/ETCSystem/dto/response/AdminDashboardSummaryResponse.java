package com.example.ETCSystem.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardSummaryResponse {
    private long totalUsers;
    private long totalVehicles;
    private long totalStations;
    private long totalTransactions; // tùy chọn: tổng giao dịch (thành công + thất bại)
    private long failedTransactions; // số giao dịch thất bại
}
