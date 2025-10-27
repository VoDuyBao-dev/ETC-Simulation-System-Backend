package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserCreationRequest {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Role role;

}
