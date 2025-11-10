package com.example.ETCSystem.controller.device;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.common.WalletDTO;
import com.example.ETCSystem.dto.request.DeviceRequest;
import com.example.ETCSystem.dto.response.DeviceResponse;
import com.example.ETCSystem.services.HandlePaymentService;
import com.example.ETCSystem.services.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TollPaymentController {
    WalletService walletService;
    HandlePaymentService handlePaymentService;

    @PostMapping("/toll-payment")
    public ApiResponse<DeviceResponse> handlePayment(@RequestBody DeviceRequest deviceRequest){
        DeviceResponse deviceResponse = handlePaymentService.handlePayment(deviceRequest);

        return ApiResponse.<DeviceResponse>builder()
                .code(200)
                .message("Thanh toán phí qua trạm thành công")
                .result(deviceResponse)
                .build();


    }
}
