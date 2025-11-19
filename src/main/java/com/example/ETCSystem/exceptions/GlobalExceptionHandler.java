package com.example.ETCSystem.exceptions;
import com.example.ETCSystem.dto.ApiResponse;
import jakarta.servlet.ServletException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //    Nếu có exception nào ngoài cái chúng ta đã bắt thì xử lí như sau:
    @ExceptionHandler(value = Exception.class)
//    Chuẩn hóa
    ResponseEntity<ApiResponse> handlingException(Exception exception){
        ApiResponse response = new ApiResponse();
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        String detailedMessage = exception.getClass().getSimpleName() + ": " + exception.getMessage();
        response.setMessage(detailedMessage);
        // In log ra console để dễ theo dõi
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
        //    xử lí Chuẩn hóa exception mình tự định nghĩa
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());

        // In chi tiết lỗi gốc nếu có để debug
        if (exception.getCause() != null) {
            System.err.println(">>> Root cause: " + exception.getCause().getMessage());
            exception.getCause().printStackTrace();
        } else {
            exception.printStackTrace();
        }

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode error = ErrorCode. UNAUTHORIZED;
        return ResponseEntity.status(error.getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(error.getCode())
                        .message(error.getMessage())
                        .build()
        );

    }

    @ExceptionHandler(value = ServletException.class)
    ResponseEntity<ApiResponse> handlingServletException(ServletException exception){
        ErrorCode error = ErrorCode. STACK_OVERFLOW;
        exception.printStackTrace();
        return ResponseEntity.status(error.getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(error.getCode())
                        .message(error.getMessage())
                        .build()
        );

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();

//        Xử lí nhập sai key Enum

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//        bắt ex do nhập sai chính tả của key Enum
        try{
            errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e){

        }

        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}

