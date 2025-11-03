package com.example.ETCSystem.controller.auth;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.OtpRequest;
import com.example.ETCSystem.dto.request.UserRequest;
import com.example.ETCSystem.dto.response.OtpResponse;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.exceptions.AppException;
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
@RequestMapping("/auth/otp")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpController {
    OtpService otpService;
    UserService userService;

    @PostMapping("/verify")
    public ApiResponse<OtpResponse> verifyOTP(@RequestBody OtpRequest otpRequest) {
        log.info("otpRequest: {}", otpRequest);
        ApiResponse<OtpResponse> apiResponse = new ApiResponse<>();
        try{
            OtpResponse otpResponse = otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtpCode());
            userService.activateUser(otpRequest.getEmail());

            apiResponse.setCode(200);
            apiResponse.setMessage("Xác thực thành công. Tài khoản của bạn đã được kích hoạt.");
            apiResponse.setResult(otpResponse);
        }catch (AppException e){
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMessage(e.getErrorCode().getMessage());
        }
        return apiResponse;

    }

    @PostMapping("/resend")
    public ApiResponse<Void> resendOTP(@RequestBody UserRequest userRequest) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        try {
            userService.findByUsername(userRequest.getUsername());
            otpService.generateAndSendOtp(userRequest.getUsername());

            apiResponse = ApiResponse.<Void>builder()
                    .code(200)
                    .message("OTP has been resent successfully. Please check your email.")
                    .build();

        } catch (AppException e) {
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMessage(e.getErrorCode().getMessage());
        }

        return apiResponse;
    }

}
