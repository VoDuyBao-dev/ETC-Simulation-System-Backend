package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.response.AdminUserResponse;
import com.example.ETCSystem.services.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/manager-users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // Hiển thị danh sách người dùng
    @GetMapping
    public ApiResponse<List<AdminUserResponse>> getAllUsers() {

        List<AdminUserResponse> users = adminUserService.getAllUsers();

        return ApiResponse.<List<AdminUserResponse>>builder()
                .code(200)
                .message("Lấy danh sách người dùng thành công")
                .result(users)
                .build();
    }

    // Cập nhật trạng thái tài khoản
    @PutMapping("/{id}/status")
    public ResponseEntity<AdminUserResponse> updateUserStatus( 
            @Valid @PathVariable Long id,
            @RequestBody AdminUpdateUserRequest request) {
        return ResponseEntity.ok(adminUserService.updateUserStatus(id, request));
    }
}
