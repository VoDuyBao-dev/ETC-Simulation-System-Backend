package com.example.ETCSystem.entities;

import com.example.ETCSystem.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fullName;
    private String email;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, CUSTOMER

    private Boolean isActive = true;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // 1 User có 1 Wallet
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    // 1 User có thể có nhiều Vehicle
    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;

    // 1 User có thể thực hiện nhiều topup
    @OneToMany(mappedBy = "user")
    private List<Topup> topups;
}
