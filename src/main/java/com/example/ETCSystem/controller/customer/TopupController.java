package com.example.ETCSystem.controller.customer;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.services.TopupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/topup")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopupController {

    TopupService topupService;

    //
    @GetMapping("/history")
    public ApiResponse<Page<TopupDTO>> getUserTopups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TopupDTO> topups = topupService.getUserTopups(page, size);
        return ApiResponse.<Page<TopupDTO>>builder()
                .code(200)
                .message("Lấy lịch sử nạp tiền thành công")
                .result(topups)
                .build();
    }
}

