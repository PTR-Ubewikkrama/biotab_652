package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cable_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CableTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    private String serialNumber;
    private Integer cableSelection;
    private Boolean visualInspection;
    private Boolean cable1;
    private Boolean cable2;
    private Boolean cable3;
    private Boolean cable4;
    private Boolean cable5;
    private Boolean cable6;
    private Boolean cable7;
    private Boolean cable8;
    private Boolean cable9;
    private Boolean cable10;
    private Boolean overallCableState;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
