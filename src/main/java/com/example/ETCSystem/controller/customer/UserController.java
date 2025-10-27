package com.example.ETCSystem.controller.customer;

import com.example.ETCSystem.dto.ApiResponse;
import com.example.ETCSystem.dto.request.UserCreationRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        log.info("userCreationRequest: {}", userCreationRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        try{
            UserResponse userResponse = userService.createUser(userCreationRequest);
            apiResponse.setCode(1000);
            apiResponse.setMessage("User created successfully");
            apiResponse.setResult(userResponse);
        }catch (AppException e){
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMessage(e.getErrorCode().getMessage());
        }
        return apiResponse;
    }

}
