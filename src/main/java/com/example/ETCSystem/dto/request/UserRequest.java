package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {

    private String username;
    private String password;
    private String confirmPassword;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Role role;

}
