package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AdminUpdateVehicleRequest;
import com.example.ETCSystem.dto.response.AdminVehicleResponse;
import com.example.ETCSystem.services.AdminVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vehicles")
@RequiredArgsConstructor
public class AdminVehicleController {

    private final AdminVehicleService adminVehicleService;

    // Lấy danh sách phương tiện
    @GetMapping
    public ApiResponse<List<AdminVehicleResponse>> getAllVehicles() {
        List<AdminVehicleResponse> vehicle = adminVehicleService.getAllVehicles();
        return ApiResponse.<List<AdminVehicleResponse>>builder()
                .code(200).message("Lấy danh sách xe thành công").result(vehicle).build();

    }

    // Cập nhật trạng thái phương tiện
    @PutMapping("/{id}/status")
    public ResponseEntity<AdminVehicleResponse> updateVehicleStatus(
            @Valid @PathVariable Long id,
            @RequestBody AdminUpdateVehicleRequest request) {
        return ResponseEntity.ok(adminVehicleService.updateVehicleStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdminVehicleResponse> deleteVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(adminVehicleService.deleteVehicle(id));
    }
}
