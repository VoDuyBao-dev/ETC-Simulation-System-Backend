package com.example.ETCSystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

// Định nghĩa các mã lỗi và message của lỗi
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), // exception chưa được đinhj
                                                                                            // nghĩa hoặc loại ex chưa
                                                                                            // bắt
    USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "user not existed", HttpStatus.NOT_FOUND),
    SAVE_USER_FAILED(1014, "save user failed", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_USER_FAILED(1015, "update user failed", HttpStatus.INTERNAL_SERVER_ERROR),
    // lỗi invalid password
    // PASSWORD_INVALID(1003, "password must at least 8 charaters"),
    // USERNAME_INVALID(1004, "username must at least 2 charaters"),
    // Xử lí nhập vào sai key Enum
    INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1017, "you do not have permission", HttpStatus.FORBIDDEN),

    INVALID_OTP(1008, "invalid otp", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1009, "email send failed", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_SAVE_FAILED(1010, "otp save failed", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_EXPIRED(1011, "otp expired", HttpStatus.BAD_REQUEST),
    OTP_NOT_FOUND(1016, "otp not found for specified email", HttpStatus.NOT_FOUND),

    // loi login
    INVALID_CREDENTIALS(1012, "invalid credentials", HttpStatus.UNAUTHORIZED),
    PASSWORDS_DO_NOT_MATCH(1013, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),

    // CANNOT_DELETE_ADMIN(1018, "cannot delete admin user", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(1019, "user not found", HttpStatus.NOT_FOUND), 
    INVALID_STATUS(1020, "invalid account status", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_ADMIN(1021, "cannot block admin user", HttpStatus.FORBIDDEN),
            
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
