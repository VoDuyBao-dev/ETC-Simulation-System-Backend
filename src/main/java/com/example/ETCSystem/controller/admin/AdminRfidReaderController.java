package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.CreateRfidReaderRequest;
import com.example.ETCSystem.dto.request.UpdateRfidReaderInfoRequest;
import com.example.ETCSystem.dto.request.UpdateRfidReaderStatusRequest;
import com.example.ETCSystem.dto.response.AdminRfidReaderResponse;
import com.example.ETCSystem.services.AdminRfidReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rfid-readers")
@RequiredArgsConstructor
public class AdminRfidReaderController {

    private final AdminRfidReaderService adminRfidReaderService;

    @GetMapping
    public ApiResponse<List<AdminRfidReaderResponse>> getAllReaders() {

        List<AdminRfidReaderResponse> readers =
                adminRfidReaderService.getAllReaders();

        return ApiResponse.<List<AdminRfidReaderResponse>>builder()
                .code(200)
                .message("Lấy danh sách RFID Reader thành công")
                .result(readers)
                .build();
    }

    @PostMapping
    public ApiResponse<AdminRfidReaderResponse> createReader(
            @Valid @RequestBody CreateRfidReaderRequest req) {

        AdminRfidReaderResponse response =
                adminRfidReaderService.createReader(req);

        return ApiResponse.<AdminRfidReaderResponse>builder()
                .code(200)
                .message("Tạo RFID Reader thành công")
                .result(response)
                .build();
    }

    @PutMapping("/{id}/status")
    public ApiResponse<AdminRfidReaderResponse> updateReaderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRfidReaderStatusRequest req) {

        AdminRfidReaderResponse response =
                adminRfidReaderService.updateReaderStatus(id, req);

        return ApiResponse.<AdminRfidReaderResponse>builder()
                .code(200)
                .message("Cập nhật trạng thái RFID Reader thành công")
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AdminRfidReaderResponse> updateReaderInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRfidReaderInfoRequest req) {

        AdminRfidReaderResponse response =
                adminRfidReaderService.updateReaderInfo(id, req);

        return ApiResponse.<AdminRfidReaderResponse>builder()
                .code(200)
                .message("Cập nhật thông tin RFID Reader thành công")
                .result(response)
                .build();
    }
}
