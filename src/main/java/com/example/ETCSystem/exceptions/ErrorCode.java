package com.example.ETCSystem.exceptions;

// Định nghĩa các mã lỗi và message của lỗi
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error"), //exception chưa được đinhj nghĩa hoặc loại ex chưa bắt
    USER_EXISTED(1002, "user existed"),
    USER_NOT_FOUND(1005, "user not found"),
    //    lỗi invalid password
    PASSWORD_INVALID(1003, "password must at least 8 charaters"),
    USERNAME_INVALID(1004, "username must at least 2 charaters"),
    //    Xử lí nhập vào sai key Enum
    INVALID_KEY(1001, "invalid message key"),
    USER_NOT_EXIST(1006, "username not exist"),
    UNAUTHENTICATED(1007, "Unauthenticated"),
    INVALID_OTP(1008, "invalid otp"),
    EMAIL_SEND_FAILED(1009, "email send failed"),
    OTP_SAVE_FAILED(1010, "otp save failed"),


    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
