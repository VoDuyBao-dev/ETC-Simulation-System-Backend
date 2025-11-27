package com.example.ETCSystem.services;

import com.example.ETCSystem.configuration.VNPAYConfig;
import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.dto.common.WalletDTO;
import com.example.ETCSystem.dto.response.VNPAYResponse;
import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.TopupRepository;
import com.example.ETCSystem.repositories.UserRepository;
import com.example.ETCSystem.util.VNPAYUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPAYService {
    VNPAYConfig vnPayConfig;
    TopupService topupService;
    UserRepository userRepository;
    TopupRepository topupRepository;
    WalletService walletService;

    public VNPAYResponse createVNPAYPayment(HttpServletRequest request) {
        try {
            // Lấy thông tin user hiện tại
            var context = SecurityContextHolder.getContext();
            String username = context.getAuthentication().getName();
            log.info("username in create VNPAY payment = {}", username);

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            // Parse parameters
            long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
            BigDecimal actualAmount = new BigDecimal(amount).divide(new BigDecimal(100));

            String bankCode = request.getParameter("bankCode");

            // Tạo topup record với status PENDING
            TopupDTO topup = topupService.createTopup(
                    user,
                    actualAmount,
                    bankCode,
                    TopupMethod.VNPAY
            );

            // Tạo VNPAY payment URL
            Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
            vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

            // Sử dụng referenceCode từ topup làm vnp_TxnRef
            vnpParamsMap.put("vnp_TxnRef", topup.getReferenceCode());
            vnpParamsMap.put("vnp_OrderInfo", "Nap tien vi ETC - " + topup.getReferenceCode());

            if (bankCode != null && !bankCode.isEmpty()) {
                vnpParamsMap.put("vnp_BankCode", bankCode);
            }
            String ip = VNPAYUtil.getIpAddress(request);

    // Tự động thay IP nội bộ → IP công cộng
            if (ip == null ||
                    ip.startsWith("192.168.") ||
                    ip.startsWith("10.") ||
                    ip.startsWith("172.") ||
                    ip.equals("127.0.0.1") ||
                    ip.contains(":")) {

                ip = "58.187.184.243";
            }

            vnpParamsMap.put("vnp_IpAddr", ip);

            // Build query URL
            String queryUrl = VNPAYUtil.getPaymentURL(vnpParamsMap, true);
            String hashData = VNPAYUtil.getPaymentURL(vnpParamsMap, false);
            String vnpSecureHash = VNPAYUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
            queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
            String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

            log.info("ip: {} ", ip);


            return VNPAYResponse.builder()
                    .code("ok")
                    .message("success")
                    .paymentUrl(paymentUrl)
                    .build();

        } catch (Exception e) {
            log.error("Error creating VNPAY payment", e);
            return VNPAYResponse.builder()
                    .code("error")
                    .message("Failed to create payment: " + e.getMessage())
                    .build();
        }
    }

    public VNPAYResponse handleVNPayCallback(HttpServletRequest request) {
        try {
            // Lấy tất cả parameters từ VNPAY
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    fields.put(fieldName, fieldValue);
                }
            }

            // Lấy và remove vnp_SecureHash
            String vnpSecureHash = fields.remove("vnp_SecureHash");


            // Verify signature
            String hashData = VNPAYUtil.getPaymentURL(fields, false);
            String signValue = VNPAYUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

            if (!signValue.equals(vnpSecureHash)) {
                log.error("Invalid signature");
                return VNPAYResponse.builder()
                        .code("97")
                        .message("Invalid signature")
                        .build();
            }

            // Lấy thông tin từ callback
            String vnpTxnRef = fields.get("vnp_TxnRef"); // referenceCode
            String vnpResponseCode = fields.get("vnp_ResponseCode");
            String vnpTransactionNo = fields.get("vnp_TransactionNo");
            String vnpBankCode = fields.get("vnp_BankCode");
            String vnpPayDate = fields.get("vnp_PayDate");
            long vnpAmount = Long.parseLong(fields.get("vnp_Amount")) / 100;

            // Tìm topup record
            Topup topup = topupRepository.findByReferenceCode(vnpTxnRef)
                    .orElseThrow(() -> new AppException(ErrorCode.TOPUP_NOT_EXISTED));

            // Kiểm tra topup đã được xử lý chưa
            if (topup.getStatus() != TopupStatus.PENDING) {
                log.warn("Topup already processed: {}", vnpTxnRef);
                return VNPAYResponse.builder()
                        .code("02")
                        .message("Order already confirmed")
                        .build();
            }

            // Kiểm tra số tiền
            if (topup.getAmount().longValue() != vnpAmount) {
                log.error("Invalid amount. Expected: {}, Actual: {}", topup.getAmount(), vnpAmount);
                topup.setStatus(TopupStatus.FAILED);
                topup.setVnpResponseCode(vnpResponseCode);
                topup.setDescription("Số tiền không khớp");
                topupRepository.save(topup);

                return VNPAYResponse.builder()
                        .code("04")
                        .message("Invalid amount")
                        .build();
            }

            // Update topup với thông tin từ VNPAY
            topup.setTransactionNo(vnpTransactionNo);
            topup.setBankCode(vnpBankCode);
            topup.setVnpResponseCode(vnpResponseCode);

            // Parse payDate
            if (vnpPayDate != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                topup.setPayDate(LocalDateTime.parse(vnpPayDate, formatter));
            }

            // Xử lý theo response code
            if ("00".equals(vnpResponseCode)) {
                // Thanh toán thành công
                topup.setStatus(TopupStatus.COMPLETED);
                topup.setCompletedAt(LocalDateTime.now());

                // Cộng tiền vào ví
                walletService.addBalance(topup.getWallet().getId(), topup.getAmount());

                // Lấy lại ví đã cập nhật từ DB
                WalletDTO updatedWallet = walletService.getWalletById(topup.getWallet().getId());

           // Lưu số dư sau giao dịch
                topup.setBalanceAfter(updatedWallet.getBalance());

                topupRepository.save(topup);

                return VNPAYResponse.builder()
                        .code("00")
                        .message("Confirm success")
                        .build();
            } else {
                // Thanh toán thất bại
                topup.setStatus(TopupStatus.FAILED);
                topup.setDescription(getVNPayErrorMessage(vnpResponseCode));
                topupRepository.save(topup);

                return VNPAYResponse.builder()
                        .code(vnpResponseCode)
                        .message("Payment failed")
                        .build();
            }

        } catch (Exception e) {
            log.error("Error handling VNPAY callback", e);
            return VNPAYResponse.builder()
                    .code("99")
                    .message("Unknown error")
                    .build();
        }
    }

    private String getVNPayErrorMessage(String responseCode) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("07", "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).");
        errorMessages.put("09", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.");
        errorMessages.put("10", "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần");
        errorMessages.put("11", "Giao dịch không thành công do: Đã hết hạn chờ thanh toán.");
        errorMessages.put("12", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.");
        errorMessages.put("13", "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP).");
        errorMessages.put("24", "Giao dịch không thành công do: Khách hàng hủy giao dịch");
        errorMessages.put("51", "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.");
        errorMessages.put("65", "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.");
        errorMessages.put("75", "Ngân hàng thanh toán đang bảo trì.");
        errorMessages.put("79", "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định.");
        return errorMessages.getOrDefault(responseCode, "Giao dịch thất bại");
    }
}
