package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Set<String> roles;
    private String status; 

}
