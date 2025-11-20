package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.enums.AccountStatus;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserResponse {
    private Long userId;
    private String fullname;
    private String email;
    private Set<String> roles;     
    private AccountStatus status;
}
