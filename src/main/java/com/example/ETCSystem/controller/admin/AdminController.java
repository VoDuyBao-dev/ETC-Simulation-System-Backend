package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    UserService userService;

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getUsers() {

        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        log.info("username {}", authenticatedUser.getName());
        log.info("role {}", authenticatedUser.getAuthorities());

        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Lấy danh sách người dùng thành công")
                .result(userService.getAllUsers())
                .build();
    }

}
