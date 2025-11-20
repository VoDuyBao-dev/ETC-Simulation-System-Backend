package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class AdminDashboardMapper {

    public RevenuePoint toRevenuePoint(int year, int month, long amount) {
        return RevenuePoint.builder()
                .year(year).month(month).amount(amount).build();
    }

    public TopStationRevenueResponse toTopStationRevenue(Object[] row) {
        // row: [stationId(Long), code(String), name(String), revenue(Long)]
        return TopStationRevenueResponse.builder()
                .stationId(((Number) row[0]).longValue())
                .stationCode((String) row[1])
                .stationName((String) row[2])
                .revenue(((BigDecimal) row[3]).longValue())
                .build();
    }

    public FailedTransactionRow toFailedRow(Object[] row) {
        // row: [txId(Long), stationCode(String), plate(String), occurredAt(Instant),
        // status(String), reason(String?)]
        return FailedTransactionRow.builder()
                .transactionId(((Number) row[0]).longValue())
                .stationCode((String) row[1])
                .plateNumber((String) row[2])
                .occurredAt((LocalDateTime) row[3])
                .status((String) row[4])
                .reason(row.length > 5 ? (String) row[5] : null)
                .build();
    }
}
