package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 6, max = 20, message = "PASSWORD_LENGTH_INVALID")
    @Pattern(regexp = "^.{6,}$", message = "PASSWORD_INVALID")
    private String password;
    private String confirmPassword;
    @NotBlank(message = "FULLNAME_REQUIRED")
    @Size(max = 50, min = 2, message = "FULLNAME_LENGTH_INVALID")
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
