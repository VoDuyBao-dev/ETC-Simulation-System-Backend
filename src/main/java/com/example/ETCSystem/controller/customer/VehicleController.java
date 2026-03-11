package com.example.ETCSystem.controller.customer;

import lombok.*;
import com.example.ETCSystem.services.VehicleService;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.dto.response.RfidTagResponse;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.RegisterVehicleRequest;
import com.example.ETCSystem.dto.request.UpdateVehicleStatusRequest;

import java.util.List;

// import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ApiResponse<VehicleResponse> register(
            @Valid @RequestBody RegisterVehicleRequest request) {

        VehicleResponse response = vehicleService.registerVehicle(request);

        return ApiResponse.<VehicleResponse>builder()
                .code(200)
                .message("Đăng ký xe thành công")
                .result(response)
                .build();
    }

    // Cập nhật trạng thái của rfidTag
    @PutMapping("/{id}/status-rfidTag")
    public ApiResponse<VehicleResponse> updateStatusRfidTag(
            @PathVariable Long id) {

        VehicleResponse response = vehicleService.updateRfidTagStatus(id);

        return ApiResponse.<VehicleResponse>builder()
                .code(200)
                .message("Cập nhật trạng thái RFID thành công")
                .result(response)
                .build();
    }

    // @PutMapping("/{id}/reissue-tag")
    // public ResponseEntity<String> reissueTag(@PathVariable Long id) {
    // vehicleService.reissueTag(id);
    // return ResponseEntity.ok("New tag issued successfully");
    // }

    @PutMapping("/{id}/status")
    public ApiResponse<VehicleResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleStatusRequest request) {

        VehicleResponse response = vehicleService.updateVehicleStatus(id, request);

        return ApiResponse.<VehicleResponse>builder()
                .code(200)
                .message("Cập nhật trạng thái xe thành công")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<VehicleResponse>> getUserVehicles() {

        List<VehicleResponse> vehicles = vehicleService.getUserVehicles();

        return ApiResponse.<List<VehicleResponse>>builder()
                .code(200)
                .message("Lấy danh sách xe thành công")
                .result(vehicles)
                .build();
    }

    @PostMapping("/{vehicleId}/report-lost-tag")
    public ApiResponse<RfidTagResponse> reportLostTag(@PathVariable Long vehicleId) {

        RfidTagResponse newTag = vehicleService.reportLostAndIssueNewTag(vehicleId);

        return ApiResponse.<RfidTagResponse>builder()
                .code(200)
                .message("Cấp lại thẻ mới thành công")
                .result(newTag)
                .build();
    }

}
