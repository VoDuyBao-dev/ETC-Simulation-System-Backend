package com.example.ETCSystem.controller.customer;

import lombok.*;
import com.example.ETCSystem.services.VehicleService;
import com.example.ETCSystem.dto.response.PagedResponse;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.dto.request.RegisterVehicleRequest;
import com.example.ETCSystem.dto.request.UpdateVehicleStatusRequest;

// import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<VehicleResponse> register(
            @RequestBody RegisterVehicleRequest request) {
        return ResponseEntity.ok(vehicleService.registerVehicle(request));
    }

    // Cập nhật trạng thái của rfidTag
    @PutMapping("/{id}/status-rfidTtag")
    public ResponseEntity<VehicleResponse> updateStatusRfidTag(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.updateRfidTagStatus(id));
    }

    // @PutMapping("/{id}/reissue-tag")
    // public ResponseEntity<String> reissueTag(@PathVariable Long id) {
    //     vehicleService.reissueTag(id);
    //     return ResponseEntity.ok("New tag issued successfully");
    // }

    @PutMapping("/{id}/status")
    public ResponseEntity<VehicleResponse> updateStatus(
            @Valid @PathVariable Long id, @RequestBody UpdateVehicleStatusRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicleStatus(id, request));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<VehicleResponse>> getUserVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(vehicleService.getUserVehicles(page, size));
    }
}
