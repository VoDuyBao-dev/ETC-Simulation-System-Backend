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
    public ApiResponse<Map<String, Long>> getStationStatistics() {

        Map<String, Long> stats =
                adminStationService.getStationStatistics();

        return ApiResponse.<Map<String, Long>>builder()
                .code(200)
                .message("Lấy thống kê trạm thành công")
                .result(stats)
                .build();
    }

    // Thêm trạm mới
    @PostMapping
    public ApiResponse<AdminStationResponse> createStation(
            @Valid @RequestBody AdminCreateStationRequest request) {

        AdminStationResponse response =
                adminStationService.createStation(request);

        return ApiResponse.<AdminStationResponse>builder()
                .code(200)
                .message("Tạo trạm thành công")
                .result(response)
                .build();
    }


    // Sửa thông tin
    @PutMapping("/{id}")
    public ApiResponse<AdminStationResponse> updateStation(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateStationRequest request) {

        AdminStationResponse response =
                adminStationService.updateStation(id, request);

        return ApiResponse.<AdminStationResponse>builder()
                .code(200)
                .message("Cập nhật thông tin trạm thành công")
                .result(response)
                .build();
    }

    // Sửa trạng thái trạm
    @PutMapping("/{id}/status")
    public ApiResponse<AdminStationResponse> updateStationStatus(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateStationRequest request) {

        AdminStationResponse response =
                adminStationService.updateStationStatus(id, request);

        return ApiResponse.<AdminStationResponse>builder()
                .code(200)
                .message("Cập nhật trạng thái trạm thành công")
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<AdminStationResponse> deleteStation(
            @PathVariable Long id) {

        AdminStationResponse response =
                adminStationService.deleteStation(id);

        return ApiResponse.<AdminStationResponse>builder()
                .code(200)
                .message("Xóa trạm thành công")
                .result(response)
                .build();
    }

}
