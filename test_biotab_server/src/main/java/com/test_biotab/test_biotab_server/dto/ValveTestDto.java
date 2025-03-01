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
public class ValveTestDto {
    private int testId;
    private int deviceId;
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
    private LocalDateTime dateTime;
}
