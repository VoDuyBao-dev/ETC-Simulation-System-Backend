package com.example.ETCSystem.controller.customer;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.UserRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Lấy thông tin người dùng thành công")
                .result(userResponse)
                .build();
    }

}
