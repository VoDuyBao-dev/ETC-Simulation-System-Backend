package com.example.ETCSystem.entities;

import com.example.ETCSystem.enums.ProcessResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tag_reads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private ProcessResult processResult;
}
