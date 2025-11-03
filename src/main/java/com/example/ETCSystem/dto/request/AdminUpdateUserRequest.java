package com.example.ETCSystem.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateUserRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
