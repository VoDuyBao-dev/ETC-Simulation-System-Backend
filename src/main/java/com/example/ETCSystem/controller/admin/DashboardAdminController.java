package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.response.AdminDashboardResponse;
import com.example.ETCSystem.services.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:3000")
public class DashboardAdminController {

    private final AdminDashboardService dashboardService;

    /**
     * API tổng hợp dữ liệu dashboard admin.
     * @param year       Năm cần thống kê (mặc định: năm hiện tại)
     * @param monthFrom  Tháng bắt đầu (1-12, mặc định 1)
     * @param monthTo    Tháng kết thúc (1-12, mặc định 12)
     * @param page       Trang cho bảng lỗi giao dịch (mặc định 0)
     * @param size       Kích thước trang (mặc định 10)
     * @param station    Mã trạm để lọc bảng lỗi (optional)
     */
    @GetMapping
    public ResponseEntity<AdminDashboardResponse> overview(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer monthFrom,
            @RequestParam(required = false) Integer monthTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, name = "station") String stationCode
    ) {
        return ResponseEntity.ok(
                dashboardService.getDashboard(year, monthFrom, monthTo, page, size, stationCode)
        );
    }
}
