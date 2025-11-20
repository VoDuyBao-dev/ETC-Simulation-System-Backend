package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.request.CreateRfidReaderRequest;
import com.example.ETCSystem.dto.request.UpdateRfidReaderInfoRequest;
import com.example.ETCSystem.dto.request.UpdateRfidReaderStatusRequest;
import com.example.ETCSystem.dto.response.AdminRfidReaderResponse;
import com.example.ETCSystem.services.AdminRfidReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rfid-readers")
@RequiredArgsConstructor
public class AdminRfidReaderController {

    private final AdminRfidReaderService adminRfidReaderService;

    @GetMapping
    public ResponseEntity<List<AdminRfidReaderResponse>> getAllReaders() {
        return ResponseEntity.ok(adminRfidReaderService.getAllReaders());
    }

    @PostMapping
    public ResponseEntity<AdminRfidReaderResponse> createReader(
            @Valid @RequestBody CreateRfidReaderRequest req) {
        return ResponseEntity.ok(adminRfidReaderService.createReader(req));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdminRfidReaderResponse> updateReaderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRfidReaderStatusRequest req) {
        return ResponseEntity.ok(adminRfidReaderService.updateReaderStatus(id, req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminRfidReaderResponse> updateReaderInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRfidReaderInfoRequest req) {
        return ResponseEntity.ok(adminRfidReaderService.updateReaderInfo(id, req));
    }
}
