package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.request.UpdateRfidTagStatusRequest;
import com.example.ETCSystem.dto.response.AdminRfidTagResponse;
import com.example.ETCSystem.services.AdminRfidTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rfid-tags")
@RequiredArgsConstructor
public class AdminRfidTagController {

    private final AdminRfidTagService adminRfidTagService;

    // Xem danh sách RFID Tag
    @GetMapping
    public ResponseEntity<List<AdminRfidTagResponse>> getAllTags() {
        return ResponseEntity.ok(adminRfidTagService.getAllRfidTags());
    }

    // Cập nhật trạng thái RFID Tag
    @PatchMapping("/{id}/status")
    public ResponseEntity<AdminRfidTagResponse> updateTagStatus(
            @PathVariable Long id,
            @RequestBody UpdateRfidTagStatusRequest req) {
        return ResponseEntity.ok(adminRfidTagService.updateRfidTagStatus(id, req));
    }
}
