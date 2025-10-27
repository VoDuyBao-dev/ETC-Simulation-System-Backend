package com.example.ETCSystem.entities;

import com.example.ETCSystem.enums.AccountStatus;
import com.example.ETCSystem.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER; // ADMIN, CUSTOMER

    // Dùng để xác định tài khoản có được phép đăng nhập không
    private Boolean enabled = false;

    // Dùng để phân biệt các trạng thái khác (bị khóa do admin chẳng hạn)
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

//    Kiểm tra nếu đã quá tgian nhưng chưa kích hoạt tài khoản thì xóa tài khoản đó
    private LocalDateTime activationExpiryTime;

    @UpdateTimestamp
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

    @PrePersist
    public void onCreate() {
        if (this.role == null) {
            this.role = Role.CUSTOMER;
        }
        if (this.enabled == null) {
            this.enabled = false;
        }
        if (this.status == null) {
            this.status = AccountStatus.PENDING;
        }
        if (this.activationExpiryTime == null) {
            this.activationExpiryTime = LocalDateTime.now().plusMinutes(3);
        }
    }
}
