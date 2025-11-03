package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.Map;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    UserService userService;

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getUsers() {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        List<UserResponse> users = userService.getAllUsers();
        apiResponse.setCode(1000);
        apiResponse.setMessage("Users retrieved successfully");
        apiResponse.setResult(users);
        return apiResponse;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("Users retrieved successfully");
        response.setResult(users);
        return ResponseEntity.ok(response);
    }

    // Lấy thông tin của 1 người dùng theo id
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id); // ném AppException nếu không tồn tại
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("User retrieved successfully");
        response.setResult(user);
        return ResponseEntity.ok(response);
    }

    // Cập nhật trạng thái tài khoản của người dùng
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> requestBody) {

        // Chỗ này dễ lỗi NullPointer nếu JSON sai
        try {
            if (requestBody == null || !requestBody.containsKey("isActive")) {
                throw new AppException(ErrorCode.INVALID_KEY);
            }

            boolean isActive = requestBody.get("isActive");
            UserResponse updatedUser = userService.updateUserStatus(id, isActive);

            ApiResponse<UserResponse> response = new ApiResponse<>();
            response.setCode(1000);
            response.setMessage("User status updated successfully");
            response.setResult(updatedUser);
            return ResponseEntity.ok(response);

        } catch (NullPointerException e) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }

    // Cập nhật thông tin người dùng (Admin)
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserInfo(
            @PathVariable Long id,
            @RequestBody @Validated AdminUpdateUserRequest request) {

        UserResponse updatedUser = userService.updateUserInfo(id, request);

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(1000);
        response.setMessage("User information updated successfully");
        response.setResult(updatedUser);

        return ResponseEntity.ok(response);
    }
}
