package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
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

    @GetMapping
    public ApiResponse<AdminDashboardResponse> overviews(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer monthFrom,
            @RequestParam(required = false) Integer monthTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, name = "station") String stationCode
    ) {

        AdminDashboardResponse response =
                dashboardService.getDashboard(
                        year, monthFrom, monthTo,
                        page, size, stationCode
                );

        return ApiResponse.<AdminDashboardResponse>builder()
                .code(200)
                .message("Lấy dữ liệu dashboard thành công")
                .result(response)
                .build();
    }
}
