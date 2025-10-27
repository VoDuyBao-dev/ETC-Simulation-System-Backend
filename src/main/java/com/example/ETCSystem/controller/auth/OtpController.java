package com.example.ETCSystem.controller.auth;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.OtpRequest;
import com.example.ETCSystem.dto.request.UserCreationRequest;
import com.example.ETCSystem.dto.response.OtpResponse;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.services.OtpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/generate")
    public ApiResponse<OtpResponse> generateAndSendOtp(@RequestBody UserCreationRequest userCreationRequest) {
        log.info("userCreationRequest: {}", userCreationRequest);
        ApiResponse<OtpResponse> apiResponse = new ApiResponse<>();
        try{
            OtpResponse otpResponse = otpService.generateAndSendOtp(userCreationRequest.getUsername());
            apiResponse.setCode(1000);
            apiResponse.setMessage("OTP sent successfully");
            apiResponse.setResult(otpResponse);
        }catch (AppException e){
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMessage(e.getErrorCode().getMessage());
        }
        return apiResponse;
    }

    @PostMapping("/verify")
    public ApiResponse<OtpResponse> verifyOTP(@RequestBody OtpRequest otpRequest) {
        log.info("otpRequest: {}", otpRequest);
        ApiResponse<OtpResponse> apiResponse = new ApiResponse<>();
        try{
            OtpResponse otpResponse = otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtpCode());
            apiResponse.setCode(1000);
            apiResponse.setMessage("OTP verified successfully");
            apiResponse.setResult(otpResponse);
        }catch (AppException e){
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMessage(e.getErrorCode().getMessage());
        }
        return apiResponse;

    }
}
