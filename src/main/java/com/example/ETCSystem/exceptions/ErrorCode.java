package com.example.ETCSystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

// Định nghĩa các mã lỗi và message của lỗi
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), //exception chưa được đinhj nghĩa hoặc loại ex chưa bắt
    USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "user not existed", HttpStatus.NOT_FOUND),
    SAVE_USER_FAILED(1014, "save user failed", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_USER_FAILED(1015, "update user failed", HttpStatus.INTERNAL_SERVER_ERROR),
    //    lỗi invalid password
//    PASSWORD_INVALID(1003, "password must at least 8 charaters"),
//    USERNAME_INVALID(1004, "username must at least 2 charaters"),
    //    Xử lí nhập vào sai key Enum
    INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1017, "you do not have permission", HttpStatus.FORBIDDEN),

    INVALID_OTP(1008, "invalid otp", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1009, "email send failed", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_SAVE_FAILED(1010, "otp save failed", HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_EXPIRED(1011, "otp expired", HttpStatus.BAD_REQUEST),
    OTP_NOT_FOUND(1016, "otp not found for specified email", HttpStatus.NOT_FOUND),

//    loi login
    INVALID_CREDENTIALS(1012, "invalid credentials", HttpStatus.UNAUTHORIZED),
    PASSWORDS_DO_NOT_MATCH(1013, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),


    SAVE_INVALIDATED_TOKEN_FAILED(1018, "save invalidated token failed", HttpStatus.INTERNAL_SERVER_ERROR),


//    lỗi liên quan đến giao dịch qua trạm
    STATION_NOT_EXISTED(1050, "station not existed", HttpStatus.INTERNAL_SERVER_ERROR),
    PLATE_NOT_EXISTED(1051, "plate not existed", HttpStatus.INTERNAL_SERVER_ERROR),
    STACK_OVERFLOW(1052, "Recursive data detected or infinite loop while processing the request.", HttpStatus.INTERNAL_SERVER_ERROR),
    WALLET_NOT_EXISTED(1053, "wallet not existed", HttpStatus.BAD_REQUEST),
    WALLET_IS_BLOCKED(1054, "wallet is blocked", HttpStatus.BAD_REQUEST),
    RFID_TAG_NOT_EXISTED(1055, "Rfid tag not existed", HttpStatus.BAD_REQUEST),
    RFID_TAG_NOT_ACTIVE(1056, "Rfid tag has been blocked or inactive", HttpStatus.BAD_REQUEST),
    VEHICLE_NOT_EXISTED(1057, "Vehicle tag not existed", HttpStatus.BAD_REQUEST),
    VEHICLE_TYPE_UNVALID(1058, "Vehicle type unvalid", HttpStatus.BAD_REQUEST),
    RFID_READER_NOT_EXISTED(1059, "Rfid reader not existed", HttpStatus.BAD_REQUEST),
    STATION_NOT_ACTIVE(1060, "Station has been maintenance or inactive", HttpStatus.BAD_REQUEST),
    RFID_READER_NOT_ACTIVE(1061, "Rfid reader has been inactive", HttpStatus.BAD_REQUEST),
    RFID_READER_NOT_BELONG_TO_STATION(1062, "Rfid reader not belong to station", HttpStatus.BAD_REQUEST),
    WALLET_BALANCE_NOT_ENOUGH(1063, "wallet balance not enough", HttpStatus.BAD_REQUEST),


// lỗi save
    SAVE_TOLL_TRANSACTION_FAILED(2000, "Save toll transaction failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SAVE_WALLET_TRANSACTION_FAILED(2001, "Save wallet transaction failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SAVE_TAG_READ_FAILED(2002, "Save tag read failed", HttpStatus.INTERNAL_SERVER_ERROR),

    WALLET_DATA_MISSING(2003, "Wallet data is missing", HttpStatus.BAD_REQUEST),
    STATION_DATA_MISSING(2004, "Station data is missing", HttpStatus.BAD_REQUEST),
    VEHICLE_DATA_MISSING(2005, "Vehicle data is missing", HttpStatus.BAD_REQUEST),
    SAVE_TOPUP_FAILED(2006, "save topup failed", HttpStatus.BAD_REQUEST),
    TOPUP_NOT_EXISTED(2007, "Topup not existed", HttpStatus.BAD_REQUEST),
    WALLET_BLOCKED(2008, "Wallet blocked", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(2009, "Email invalid", HttpStatus.BAD_REQUEST),


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
