package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.response.AdminUserResponse;
import com.example.ETCSystem.services.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/admin/manager-users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // Hiển thị danh sách người dùng  có phân trang
    @GetMapping
    public ResponseEntity<Page<AdminUserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(adminUserService.getAllUsers(page, size));
    }

    // Cập nhật trạng thái tài khoản
    @PutMapping("/{id}/status")
    public ResponseEntity<AdminUserResponse> updateUserStatus( 
            @Valid @PathVariable Long id,
            @RequestBody AdminUpdateUserRequest request) {
        return ResponseEntity.ok(adminUserService.updateUserStatus(id, request));
    }
}
