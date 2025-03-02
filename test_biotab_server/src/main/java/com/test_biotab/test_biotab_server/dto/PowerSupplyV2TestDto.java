package com.test_biotab.test_biotab_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PowerSupplyV2TestDto {
    private Integer testId;
    private int deviceId;
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
    private Boolean status;
    private LocalDateTime dateTime;
}
