package com.example.ETCSystem.controller.auth;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.AuthenticationRequest;
import com.example.ETCSystem.dto.request.UserRequest;
import com.example.ETCSystem.dto.response.AuthenticationResponse;
import com.example.ETCSystem.dto.response.OtpResponse;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.services.AuthenticationService;
import com.example.ETCSystem.services.OtpService;
import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserService userService;
    OtpService otpService;
    AuthenticationService authenticationService;


    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        log.info("userRequest: {}", userRequest);

        UserResponse userResponse = userService.createUser(userRequest);
        otpService.generateAndSendOtp(userRequest.getUsername());
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Đăng ký thành công. Vui lòng kiểm tra email để xác thực tài khoản.")
                .result(userResponse)
                .build();


    }

    @PostMapping("/login")
    public  ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Đăng nhập thành công")
                .result(authenticationResponse)
                .build();

    }
}
