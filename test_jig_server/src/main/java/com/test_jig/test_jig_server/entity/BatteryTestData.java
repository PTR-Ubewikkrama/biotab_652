package com.test_jig.test_jig_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "battery_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatteryTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private String qrCode;

    private Double maximumCurrent;

    private Double maxCurrentDrawnTime;

    private boolean maxCurrentCutOff;

    private Double normalCurrent;

    private Double normalCurrentDrawnTime;

    private boolean normalCurrentCutOff;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}