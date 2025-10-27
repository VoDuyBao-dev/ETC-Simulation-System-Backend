package com.example.ETCSystem.controller.auth;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.UserCreationRequest;
import com.example.ETCSystem.dto.response.OtpResponse;
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

    @PostMapping
    public ApiResponse<OtpResponse> generateAndSendOtp(@RequestBody UserCreationRequest userCreationRequest) {
        log.info("userCreationRequest: {}", userCreationRequest);
        ApiResponse<OtpResponse> apiResponse = new ApiResponse<>();
        try{
            OtpResponse otpResponse = otpService.generateAndSendOtp(userCreationRequest.getUsername());
            apiResponse.setCode(1000);
            apiResponse.setMessage("OTP sent successfully");
            apiResponse.setResult(otpResponse);
        }catch (Exception e){
            apiResponse.setCode(5000);
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }
}
