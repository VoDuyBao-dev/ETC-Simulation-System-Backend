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
    private String password;
    private String confirmPassword;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Role role;

}
