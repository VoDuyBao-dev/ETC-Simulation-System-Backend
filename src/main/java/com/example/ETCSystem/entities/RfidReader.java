package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rfid_readers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RfidReader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(unique = true)
    private String readerUid;

    private String description;
    private LocalDateTime lastHeartbeat;
    private Boolean isActive = true;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reader")
    private List<TagRead> tagReads;
}
