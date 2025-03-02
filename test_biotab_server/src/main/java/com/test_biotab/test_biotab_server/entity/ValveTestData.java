package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "valve_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValveTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    private Double idleVoltageLowThresh;
    private Double idleVoltageUpThresh;
    private Double idleCurrentUpThresh;
    private Double loadVoltageLowThresh;
    private Double loadVoltageUpThresh;
    private Double loadCurrentUpThresh;
    private Double setPressure;
    private String serialNumber;
    private Double idleVoltage;
    private Boolean idleVoltageStatus;
    private Double idleCurrent;
    private Boolean idleCurrentStatus;
    private Double coilResistance;
    private Double operatingCurrent;
    private Double peakPower;
    private Double averagePower;
    private Double flowRate;
    private Boolean flowRateStatus;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}