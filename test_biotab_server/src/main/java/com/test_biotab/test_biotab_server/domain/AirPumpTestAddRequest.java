package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class AirPumpTestAddRequest {
    private String hashKey;
    private String deviceMac;
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
}
