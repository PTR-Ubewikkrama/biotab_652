package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "air_pump_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirPumpTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    private Double idleVolLowTh;

    private Double idleVolUpTh;

    private Double idleCurUpTh;

    private Double loadVolLowTh;

    private Double loadVolUp;

    private Double loadCurUpTh;

    private Double setPressure;

    private String serialNumber;

    private Double idleVol;

    private Boolean idleVolStatus;

    private Double idleCurrent;

    private Boolean idleCurrentStatus;

    private Double loadVoltage;

    private Boolean loadVoltageStatus;

    private Double loadCurrent;

    private Boolean loadCurrentStatus;

    private Double maxPressure;

    private Boolean maxPressureStatus;

    private Boolean noiseLevel;

    private String deviceStatus;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
