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

import java.util.List;

@RestController
@RequestMapping("/admin/toll-transactions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TollTransactionAdminController {
    TollTransactionService tollTransactionService;


//    trả về list
    @GetMapping("/transactions")
    public ApiResponse<List<TransactionHistoryAdminResponse>> getAllHistory() {

                List<TransactionHistoryAdminResponse> result = tollTransactionService.getAllHistory();
        return ApiResponse.<List<TransactionHistoryAdminResponse>>builder()
                .code(200)
                .message("Lấy toàn bộ lịch sử giao dịch qua trạm thành công")
                .result(result)
                .build();
    }
}
