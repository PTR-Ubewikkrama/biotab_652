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
public class AirPumpV2TestDto {
    private int testId;
    private int deviceId;
    private Double flowRateLowThresh;
    private Double flowRateUpThresh;
    private Double loadVoltageLowThresh;
    private Double loadVoltageUpThresh;
    private Double loadCurrentUpThresh;
    private Double pressureLowThresh;
    private Double pressureUpThresh;
    private String serialNumber;
    private Double pressure;
    private Boolean pressureStatus;
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
