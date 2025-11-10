package com.example.ETCSystem.controller.customer;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.response.TransactionHistoryResponse;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.repositories.UserRepository;
import com.example.ETCSystem.services.UserService;
import com.example.ETCSystem.services.WalletTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/wallet")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {
    WalletTransactionService walletTransactionService;
    UserService userService;

    @GetMapping("/history")
    public ApiResponse<Page<TransactionHistoryResponse>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        UserResponse userResponse = userService.getMyInfo();
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionHistoryResponse> result = walletTransactionService.getHistory(userResponse.getUserId(), pageable);
        return ApiResponse.<Page<TransactionHistoryResponse>>builder()
                .code(200)
                .message("Lấy lịch sử giao dịch thành công")
                .result(result)
                .build();
    }
}
