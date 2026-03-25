package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AdminUpdateVehicleRequest;
import com.example.ETCSystem.dto.response.AdminVehicleResponse;
import com.example.ETCSystem.services.AdminVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

        List<AdminVehicleResponse> vehicles = adminVehicleService.getAllVehicles();

        return ApiResponse.<List<AdminVehicleResponse>>builder()
                .code(200)
                .message("Lấy danh sách xe thành công")
                .result(vehicles)
                .build();
    }

    // Cập nhật trạng thái phương tiện
    @PutMapping("/{id}/status")
    public ApiResponse<AdminVehicleResponse> updateVehicleStatus(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateVehicleRequest request) {

        AdminVehicleResponse response = adminVehicleService.updateVehicleStatus(id, request);

        return ApiResponse.<AdminVehicleResponse>builder()
                .code(200)
                .message("Cập nhật trạng thái xe thành công")
                .result(response)
                .build();
    }

    // @DeleteMapping("/{id}")
    // public ApiResponse<AdminVehicleResponse> deleteVehicle(
    // @PathVariable Long id) {

    // AdminVehicleResponse response =
    // adminVehicleService.deleteVehicle(id);

    // return ApiResponse.<AdminVehicleResponse>builder()
    // .code(200)
    // .message("Xóa phương tiện thành công")
    // .result(response)
    // .build();
    // }
}
