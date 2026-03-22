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
    PHONE_EXISTED(1016, "phone existed", HttpStatus.BAD_REQUEST),

    // lỗi invalid password

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

    // Admin manage users
    // CANNOT_DELETE_ADMIN(1018, "cannot delete admin user", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(1019, "not found user in database", HttpStatus.NOT_FOUND),
    USER_REUIRED(1020, "user id is required", HttpStatus.BAD_REQUEST),
    STATUS_REQUIRED(1021, "status is required", HttpStatus.BAD_REQUEST),
    INVALID_PAGINATION(1022, "invalid pagination parameters", HttpStatus.BAD_REQUEST),
    CANNOT_CHANGE_ADMIN_STATUS(1023, "cannot change status of admin user", HttpStatus.FORBIDDEN),
    INVALID_STATUS(1024, "Status not valid", HttpStatus.BAD_REQUEST),

    // Admin manage stations
    STATION_NOT_FOUND(1300, "Station not found", HttpStatus.NOT_FOUND),
    STATION_CODE_EXISTS(1301, "Station code already exists", HttpStatus.CONFLICT),
    SAVE_INVALIDATED_TOKEN_FAILED(1018, "save invalidated token failed", HttpStatus.INTERNAL_SERVER_ERROR),
    STATION_LOCATION_EXISTS(1302, "Trùng khinh độ và vĩ độ", HttpStatus.CONFLICT),

    // ===== VALIDATION ERRORS =====
    NAME_REQUIRED(1001, "Name is required", HttpStatus.BAD_REQUEST),
    ADDRESS_REQUIRED(1002, "Address is required", HttpStatus.BAD_REQUEST),
    LATITUDE_REQUIRED(1003, "Latitude is required", HttpStatus.BAD_REQUEST),
    LONGITUDE_REQUIRED(1004, "Longitude is required", HttpStatus.BAD_REQUEST),

    STATION_REQUIED(1005, "Station id is required", HttpStatus.BAD_REQUEST),
    STATION_NAME_EXISTS(1303, "Station name already exists", HttpStatus.CONFLICT),
    STATION_ADDRESS_EXISTS(1304, "Station address already exists", HttpStatus.CONFLICT),
    NO_UPDATE_FIELDS(1305, "No fields to update", HttpStatus.BAD_REQUEST),
    IS_ACTIVATE_REQUIRED(1306, "isActivate field is required", HttpStatus.BAD_REQUEST),

    // lỗi liên quan đến giao dịch qua trạm
    STATION_NOT_EXISTED(1050, "station not existed", HttpStatus.INTERNAL_SERVER_ERROR),
    PLATE_NOT_EXISTED(1051, "plate not existed", HttpStatus.INTERNAL_SERVER_ERROR),
    STACK_OVERFLOW(1052, "Recursive data detected or infinite loop while processing the request.",
            HttpStatus.INTERNAL_SERVER_ERROR),
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
    WALLET_EXISTED(1064, "wallet existed", HttpStatus.BAD_REQUEST),

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
    NO_ACTIVE_RFID_TAG(2010, "NO ACTIVE RFID TAG", HttpStatus.BAD_REQUEST),

    // Admin manage vehicles
    VEHICLE_NOT_FOUND(1200, "Vehicle not found", HttpStatus.NOT_FOUND),
    INVALID_VEHICLE_STATUS(1201, "Invalid vehicle status", HttpStatus.BAD_REQUEST),
    VEHICLE_ALREADY_ACTIVE(1202, "Vehicle is already active", HttpStatus.CONFLICT),
    VEHICLE_ALREADY_INACTIVE(1203, "Vehicle is already inactive", HttpStatus.CONFLICT),
    VEHICLE_ID_REQUIRED(1204, "vehicle id is required", HttpStatus.BAD_REQUEST),

    // RFID_Tag
    RFID_TAG_NOT_FOUND(1210, "RFID not found", HttpStatus.BAD_REQUEST),
    // RFID_Reader
    READER_UID_EXISTS(1220, "rfid_reader is already exists", HttpStatus.CONFLICT),
    READER_NOT_FOUND(1221, "rfid_reader not found", HttpStatus.NOT_FOUND),
    READER_ID_REQUIRED(1222, "Reader UID cannot be blank", HttpStatus.BAD_REQUEST),
    STATION_ID_REQUIRED(1223, "Station ID cannot be null", HttpStatus.BAD_REQUEST),
    PLATE_NUMBER_REQUIRED(3002, "Plate number is required", HttpStatus.BAD_REQUEST),
    VEHICLE_TYPE_REQUIRED(3003, "Vehicle type is required", HttpStatus.BAD_REQUEST),
    GOVERNMENT_VEHICLE_RESTRICTED(3004, "Government vehicles cannot be registered", HttpStatus.BAD_REQUEST),
    INVALID_PLATE_FORMAT(3005, "Invalid plate number format", HttpStatus.BAD_REQUEST),
    VEHICLE_ALREADY_EXISTS(3006, "Vehicle with the same plate number already exists", HttpStatus.CONFLICT),

    // lỗi không có permission
    FORBIDDEN(4000, "You do not have permission to access this topup", HttpStatus.FORBIDDEN),
    INVALID_AMOUNT(4001, "Amount must be between 5000 and less than 1000000000", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ADMIN(4002, "You are not allowed to access admin", HttpStatus.FORBIDDEN),
    PHONE_INVALID(4003, "Phone number invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2011, "Password must be at least 6 characters", HttpStatus.BAD_REQUEST),

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
