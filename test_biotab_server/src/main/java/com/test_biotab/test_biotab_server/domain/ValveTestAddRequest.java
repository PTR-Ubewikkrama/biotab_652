package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class ValveTestAddRequest {
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
    private Double coilResistance;
    private Double operatingCurrent;
    private Double peakPower;
    private Double averagePower;
    private Double flowRate;
    private Boolean flowRateStatus;
}
