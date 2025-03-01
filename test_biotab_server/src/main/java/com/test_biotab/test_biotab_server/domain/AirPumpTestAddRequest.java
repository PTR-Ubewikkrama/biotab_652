package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class AirPumpTestAddRequest {
    private String hashKey;

    private String deviceMac;

    private double idleVolLowTh;

    private double idleVolUpTh;

    private double idleCurUpTh;

    private double loadVolLowTh;

    private double loadVolUp;

    private double loadCurUpTh;

    private double setPressure;

    private String serialNumber;

    private double idleVol;

    private Boolean idleVolStatus;

    private double idleCurrent;

    private Boolean idleCurrentStatus;

    private double loadVoltage;

    private Boolean loadVoltageStatus;

    private double loadCurrent;

    private Boolean loadCurrentStatus;

    private double flowRate;

    private Boolean flowRateStatus;

    private Boolean noiseLevel;

    private String deviceStatus;
}
