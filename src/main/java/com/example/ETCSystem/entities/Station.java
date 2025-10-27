package com.example.ETCSystem.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ETCSystem.enums.StationStatus;

@Entity
@Table(name = "stations")

public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private StationStatus status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "station")
    private List<RfidReader> readers;
}
