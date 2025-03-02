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
    private String qrCode;
    private Integer cableSelection;
    private String visualInspection;
    private String cable1;
    private String cable2;
    private String cable3;
    private String cable4;
    private String cable5;
    private String cable6;
    private String cable7;
    private String cable8;
    private String cable9;
    private String cable10;
    private String overallCableState;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
