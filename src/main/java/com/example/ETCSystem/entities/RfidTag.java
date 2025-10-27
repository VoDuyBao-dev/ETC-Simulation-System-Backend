package com.example.ETCSystem.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.example.ETCSystem.enums.TagStatus;

@Entity
@Table(name = "rfid_tags")

public class RfidTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tagUid;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime issuedAt;

    @Enumerated(EnumType.STRING)
    private TagStatus status; // active, lost, blocked, inactive
}
