package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.ETCSystem.enums.VehicleType;
import com.example.ETCSystem.enums.VehicleStatus;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private String brand;
    private String model;
    private String color;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_delete", nullable = false)
    private Integer isDelete = 0;

    @OneToMany(mappedBy = "vehicle")
    private List<RfidTag> rfidTags;

    @OneToMany(mappedBy = "vehicle")
    private List<TollTransaction> tollTransactions;


}
