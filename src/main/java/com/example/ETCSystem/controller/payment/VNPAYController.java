package com.example.ETCSystem.controller.payment;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.dto.response.VNPAYResponse;
import com.example.ETCSystem.services.TopupService;
import com.example.ETCSystem.services.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPAYController {
    final VNPAYService vnpayService;

    @Value("${payment.vnPay.mobileDeepLink}")
    String mobileDeepLink;

    final TopupService topupService;

    @GetMapping("/vn-pay")
    public ApiResponse<VNPAYResponse> pay(HttpServletRequest request) {
        VNPAYResponse response = vnpayService.createVNPAYPayment(request);
        return ApiResponse.<VNPAYResponse>builder()
                .code(200)
                .result(response)
                .build();
    }


    @GetMapping("/vn-pay-callback")
    public RedirectView vnpayCallback(HttpServletRequest request) {
        log.info("Received VNPAY callback");

        VNPAYResponse response = vnpayService.handleVNPayCallback(request);

        // Redirect về mobile app qua deep link
        String redirectUrl = String.format("%s?code=%s&message=%s",
                mobileDeepLink,
                response.getCode(),
                URLEncoder.encode(response.getMessage(), StandardCharsets.UTF_8)
        );

        log.info("Redirecting to: {}", redirectUrl);

        return new RedirectView(redirectUrl);
    }

//     Frontend check trạng thái topup
    @GetMapping("/topup/status/{referenceCode}")
    public ApiResponse<TopupDTO> getTopupStatus(@PathVariable String referenceCode) {
        log.info("Checking topup status for reference: {}", referenceCode);

        TopupDTO topup = topupService.getTopupByReferenceCode(referenceCode);

        return ApiResponse.<TopupDTO>builder()
                .code(200)
                .message("Lấy trạng thái topup thành công")
                .result(topup)
                .build();
    }

}
