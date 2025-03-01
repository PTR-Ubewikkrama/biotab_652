package com.test_jig.test_jig_server.entity;

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

    private Double idleVolLowTh;

    private Double idleVolUpTh;

    private Double loadVolLowTh;

    private Double loadVolUpTh;

    private Double loadCurUpTh;

    private String serialNumber;

    private Double idleVol;

    private Boolean idleVolStatus;

    private Double loadVol;

    private Boolean loadVolStatus;

    private Double loadCurrent;

    private Boolean loadCurrentStatus;

    private Double operatingPower;

    private Boolean noiseLevel;

    private String deviceStatus;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}