package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AdminCreateStationRequest;
import com.example.ETCSystem.dto.request.AdminUpdateStationRequest;
import com.example.ETCSystem.dto.response.AdminStationResponse;
import com.example.ETCSystem.services.AdminStationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/stations")
@RequiredArgsConstructor
public class AdminStationController {

    private final AdminStationService adminStationService;

    @GetMapping
    public ApiResponse<List<AdminStationResponse>> getAllStations() {
        List<AdminStationResponse> stations = adminStationService.getAllStations();

        return ApiResponse.<List<AdminStationResponse>>builder()
                .code(200)
                .message("Lấy danh sách trạm thành công")
                .result(stations)
                .build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getStationStatistics() {
        Map<String, Long> stats = adminStationService.getStationStatistics();
        return ResponseEntity.ok(stats);
    }

    // Thêm trạm mới
    @PostMapping
    public ResponseEntity<AdminStationResponse> createStation(
            @Valid @RequestBody AdminCreateStationRequest request) {
        return ResponseEntity.ok(adminStationService.createStation(request));
    }

    // Sửa thông tin
    @PutMapping("/{id}")
    public ResponseEntity<AdminStationResponse> updateStation(
            @Valid @PathVariable Long id,
            @RequestBody AdminUpdateStationRequest request) {
        return ResponseEntity.ok(adminStationService.updateStation(id, request));
    }

    // Sửa trạng thái trạm
    @PutMapping("/{id}/status")
    public ResponseEntity<AdminStationResponse> updateStationStatus(
            @Valid @PathVariable Long id,
            @RequestBody AdminUpdateStationRequest request) {
        return ResponseEntity.ok(adminStationService.updateStationStatus(id, request));
    }
}
