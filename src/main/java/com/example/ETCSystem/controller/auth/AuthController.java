package com.example.ETCSystem.controller.auth;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.*;
import com.example.ETCSystem.dto.response.AuthenticationResponse;
import com.example.ETCSystem.dto.response.IntrospectResponse;
import com.example.ETCSystem.dto.response.OtpResponse;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.services.AuthenticationService;
import com.example.ETCSystem.services.OtpService;
import com.example.ETCSystem.services.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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

    @PostMapping("/logout")
    public  ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Đăng xuất thành công")
                .build();
    }

    @PostMapping("/introspect")
    public  ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .result(introspectResponse)
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Làm mới token thành công")
                .result(authenticationResponse)
                .build();
    }

    @PatchMapping("/updateInfo")
    public ApiResponse<UserResponse> updateUserInfo(@RequestBody UserRequest userRequest) {
        log.info("userRequest {}:", userRequest.toString());
        UserResponse userResponse = userService.updateUserInfo(userRequest);
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Cập nhật thông tin người dùng thành công")
                .result(userResponse)
                .build();
    }

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
