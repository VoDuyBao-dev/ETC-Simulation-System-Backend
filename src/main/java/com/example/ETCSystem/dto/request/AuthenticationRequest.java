package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "EMAIL_INVALID"
    )
    private String username;
    @NotBlank(message = "PASSWORD_REQUIRED")
    @Pattern(regexp = "^.{6,}$", message = "PASSWORD_INVALID")
    private String password;
}
