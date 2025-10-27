package com.example.ETCSystem.services;


import com.example.ETCSystem.dto.response.OtpResponse;
import com.example.ETCSystem.entities.OtpVerification;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.OtpVerificationRepository;
import com.example.ETCSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;
    @Autowired
    private EmailService emailService;

    // Tạo và gửi OTP
    public OtpResponse generateAndSendOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpVerification otpVerification = OtpVerification.builder()
                .email(email)
                .otpCode(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();

        try{
            otpVerificationRepository.save(otpVerification);
        }catch (Exception e){
            log.error("Lỗi khi lưu OTP: ",e);
            throw new AppException(ErrorCode.OTP_SAVE_FAILED, e);
        }

        try {
            emailService.sendOtpEmail(email, otp);

        }catch (Exception e){
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED, e);
        }

        OtpResponse otpResponse = OtpResponse.builder()
                .email(email)
                .status(true)
                .otp(otp)
                .build();

        return otpResponse;

    }

//    ktra otp
    public OtpResponse verifyOtp(String email, String otp) {
        OtpVerification otpVerification = otpVerificationRepository.findTopByEmailOrderByCreatedAtDesc(email);

        if(otpVerification == null){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }else if (otpVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        } else if (otpVerification.isUsed() || !otpVerification.getOtpCode().equals(otp)) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        otpVerification.setUsed(true);
        otpVerificationRepository.save(otpVerification);
        OtpResponse otpResponse = OtpResponse.builder()
                .email(email)
                .status(true)
                .build();
        return otpResponse;
    }


}
