package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
