package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.StationCreateRequest;
import com.example.ETCSystem.dto.request.StationUpdateRequest;
import com.example.ETCSystem.dto.response.StationResponse;
import com.example.ETCSystem.services.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/stations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StationResponse>>> getAllStations() {
        var response = ApiResponse.<List<StationResponse>>builder()
                .code(200)
                .message("Danh sách trạm thu phí")
                .result(stationService.getAllStations())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StationResponse>> getStationById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<StationResponse>builder()
                .code(200)
                .message("Thông tin trạm")
                .result(stationService.getStationById(id))
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StationResponse>> createStation(@RequestBody StationCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.<StationResponse>builder()
                .code(201)
                .message("Tạo trạm thành công")
                .result(stationService.createStation(request))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StationResponse>> updateStation(@PathVariable Long id, @RequestBody StationUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.<StationResponse>builder()
                .code(200)
                .message("Cập nhật trạm thành công")
                .result(stationService.updateStation(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa trạm thành công")
                .build());
    }
}
