package com.example.ETCSystem.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class AdminDashboardResponse {
    private AdminDashboardSummaryResponse summary;
    private List<RevenuePoint> revenueByMonth;                // line chart
    private List<TopStationRevenueResponse> top5Stations;     // bar chart
    private List<FailedTransactionRow> failedTransactions;    // table (page đầu)
    // private int page;     // paging info cho bảng lỗi
    // private int size;
    private long totalFailed; // tổng số dòng lỗi để FE phân trang
}
