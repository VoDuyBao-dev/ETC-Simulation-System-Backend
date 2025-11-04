package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.VehicleCreateRequest;
import com.example.ETCSystem.dto.request.VehicleUpdateRequest;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vehicles")
@RequiredArgsConstructor
// @PreAuthorize("hasRole('ADMIN')")
public class AdminVehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getAllVehicles() {
        var response = ApiResponse.<List<VehicleResponse>>builder()
                .code(200)
                .message("Danh sách phương tiện")
                .result(vehicleService.getAllVehicles())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicle(@PathVariable Long id) {
        var response = ApiResponse.<VehicleResponse>builder()
                .code(200)
                .message("Thông tin phương tiện")
                .result(vehicleService.getVehicleById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(@RequestBody VehicleCreateRequest request) {
        var response = ApiResponse.<VehicleResponse>builder()
                .code(201)
                .message("Thêm phương tiện thành công")
                .result(vehicleService.createVehicle(request))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(
            @PathVariable Long id, @RequestBody VehicleUpdateRequest request) {
        var response = ApiResponse.<VehicleResponse>builder()
                .code(200)
                .message("Cập nhật phương tiện thành công")
                .result(vehicleService.updateVehicle(id, request))
                .build();
        return ResponseEntity.ok(response);
    }


}
