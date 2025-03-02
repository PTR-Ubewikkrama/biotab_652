package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class AirPumpV2TestAddRequest {
    private String hashKey;
    private String deviceMac;
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
}
