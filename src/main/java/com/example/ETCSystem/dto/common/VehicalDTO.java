package com.example.ETCSystem.dto.common;

import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.TollTransaction;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicalDTO {


    private Long id;


    private User user;

    private String plateNumber;

    private VehicleType vehicleType;
    private String brand;
    private String model;
    private String color;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private RfidTag rfidTag;
    private List<TollTransaction> tollTransactions;
}
