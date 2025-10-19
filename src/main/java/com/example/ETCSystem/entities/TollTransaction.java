package com.example.ETCSystem.entities;

import com.example.ETCSystem.enums.TollStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "toll_transactions")

public class TollTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne @JoinColumn(name = "rfid_tag_id")
    private RfidTag rfidTag;

    @ManyToOne @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private RfidReader reader;

    @Column(nullable = false)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    private TollStatus status;

    private String note;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
