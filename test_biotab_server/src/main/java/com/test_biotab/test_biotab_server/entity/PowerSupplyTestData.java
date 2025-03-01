package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "power_supply_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PowerSupplyTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    private double idleVoltageLowTh;
    private double idleVoltageUpTh;
    private double loadVoltageLowTh;
    private double loadVoltageUpTh;
    private double loadCurrentUpTh;
    private String serialNumber;
    private double idleVol;
    private Boolean idleVolStatus;
    private double loadVol;
    private Boolean loadVolStatus;
    private double loadCurrent;
    private Boolean loadCurrentStatus;
    private double operatingPower;
    private Boolean noiseLevel;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}