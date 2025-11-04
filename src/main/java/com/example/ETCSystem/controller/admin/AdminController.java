package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.request.UpdateStatusRequest;
import com.example.ETCSystem.dto.response.UserResponse;
// import com.example.ETCSystem.entities.User;
// import com.example.ETCSystem.exceptions.AppException;
// import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.services.AdminUserService;
// import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    // UserService userService;
    AdminUserService adminUserService;

    // @GetMapping("/users")
    // public ApiResponse<List<UserResponse>> getUsers() {

    //     var authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
    //     log.info("username {}", authenticatedUser.getName());
    //     log.info("role {}", authenticatedUser.getAuthorities());

    //     return ApiResponse.<List<UserResponse>>builder()
    //             .code(200)
    //             .message("Lấy danh sách người dùng thành công")
    //             .result(userService.getAllUsers())
    //             .build();
    // }

    // Lấy danh sách tất cả người dùng
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User '{}' with roles {} is requesting user list",
                auth.getName(), auth.getAuthorities());

        List<UserResponse> users = adminUserService.getAllUsers();

        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Users retrieved successfully")
                .result(users)
                .build();

        return ResponseEntity.ok(response);
    }

    // Lấy thông tin của 1 người dùng theo id
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = adminUserService.getUserById(id); // ném AppException nếu không tồn tại
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("User retrieved successfully");
        response.setResult(user);
        return ResponseEntity.ok(response);
    }

    // Cập nhật trạng thái tài khoản của người dùng
    @PutMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {

        UserResponse updatedUser = adminUserService.updateUserStatus(id, request.getStatus());

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("User status updated successfully");
        response.setResult(updatedUser);

        return ResponseEntity.ok(response);
    }

    // Cập nhật thông tin người dùng (Admin chỉnh sửa thông tin người dùng)
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserInfo(
            @PathVariable Long id,
            @RequestBody @Validated AdminUpdateUserRequest request) {

        UserResponse updatedUser = adminUserService.updateUserInfo(id, request);

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("User information updated successfully");
        response.setResult(updatedUser);

        return ResponseEntity.ok(response);
    }
}
