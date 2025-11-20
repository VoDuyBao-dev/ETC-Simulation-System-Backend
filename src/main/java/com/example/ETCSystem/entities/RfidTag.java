package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.example.ETCSystem.enums.TagStatus;
import lombok.*;

@Entity
@Table(name = "rfid_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

//    Thời điểm xe thanh toán thành công lần cuối
    @Column(name = "last_successful_passage")
    private LocalDateTime lastSuccessfulPassage;
// trạm mà xe vừa qua thành công lần cuối
    @Column(name = "last_passage_station_id")
    private Long lastPassageStationId;

    @Enumerated(EnumType.STRING)
    private TagStatus status; // active, lost, blocked, inactive
}
