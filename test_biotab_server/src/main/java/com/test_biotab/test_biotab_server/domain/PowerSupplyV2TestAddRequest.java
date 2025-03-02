package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class PowerSupplyV2TestAddRequest {
    private String hashKey;
    private String deviceMac;
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
}
