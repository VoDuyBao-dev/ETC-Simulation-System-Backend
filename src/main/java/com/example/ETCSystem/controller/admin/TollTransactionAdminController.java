package com.example.ETCSystem.controller.admin;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.response.TransactionHistoryAdminResponse;
import com.example.ETCSystem.services.TollTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/toll-transactions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TollTransactionAdminController {
    TollTransactionService tollTransactionService;


    @GetMapping("/transactions")
    public ApiResponse<Page<TransactionHistoryAdminResponse>> getAllHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionHistoryAdminResponse> result = tollTransactionService.getAllHistory(pageable);
        return ApiResponse.<Page<TransactionHistoryAdminResponse>>builder()
                .code(200)
                .message("Lấy toàn bộ lịch sử giao dịch qua trạm thành công")
                .result(result)
                .build();
    }
}
