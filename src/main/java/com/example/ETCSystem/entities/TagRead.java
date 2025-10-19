package com.example.ETCSystem.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tag_reads")

public class TagRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private RfidReader reader;

    @Column(nullable = false)
    private String tagUid;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime readTime;
    private Boolean processed = false;
    private String processResult;
}
