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
public class AirPumpTestDto {
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
    private Double loadVoltage;
    private Boolean loadVoltageStatus;
    private Double loadCurrent;
    private Boolean loadCurrentStatus;
    private Double flowRate;
    private Boolean flowRateStatus;
    private Boolean noiseLevelStatus;
    private Boolean status;
    private LocalDateTime dateTime;
}
