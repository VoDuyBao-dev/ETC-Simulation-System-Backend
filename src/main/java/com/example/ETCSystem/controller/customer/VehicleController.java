package com.example.ETCSystem.controller.customer;

import lombok.*;
import com.example.ETCSystem.services.VehicleService;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.dto.request.RegisterVehicleRequest;
import com.example.ETCSystem.dto.request.UpdateVehicleStatusRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/user/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<VehicleResponse> register(@RequestBody RegisterVehicleRequest request,
                                                 @RequestParam Long userId) {
        return ResponseEntity.ok(vehicleService.registerVehicle(userId, request));
    }

    @PutMapping("/{id}/report-lost")
    public ResponseEntity<String> reportLost(@PathVariable Long id) {
        vehicleService.reportLostTag(id);
        return ResponseEntity.ok("Report lost successfully");
    }

    @PutMapping("/{id}/reissue-tag")
    public ResponseEntity<String> reissueTag(@PathVariable Long id) {
        vehicleService.reissueTag(id);
        return ResponseEntity.ok("New tag issued successfully");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<VehicleResponse> updateStatus(@PathVariable Long id,
                                                     @RequestBody UpdateVehicleStatusRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicleStatus(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getUserVehicles(@RequestParam Long userId, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(vehicleService.getUserVehicles(userId, page, size));
    }
}
