package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "EMAIL_INVALID"
    )
    private String username;
    @Pattern(regexp = "^.{6,}$", message = "PASSWORD_INVALID")
    private String password;
    private String confirmPassword;
    private String fullname;
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "EMAIL_INVALID"
    )
    private String email;
    @Pattern(regexp = "^0\\d{9}$", message = "PHONE_INVALID")
    private String phone;
    private String address;
    private Role role;

}
