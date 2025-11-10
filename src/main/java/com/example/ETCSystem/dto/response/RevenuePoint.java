package com.example.ETCSystem.dto.response;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class RevenuePoint {
    private int year;
    private int month;
    private long amount; // tổng doanh thu tháng (đơn vị: VND)
}
