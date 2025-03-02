package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class PowerPCBV2TestAddRequest {
    private String hashKey;
    private String deviceMac;
    private Double loadVoltageLowThresh;
    private String serialNumber;
    private Double loadCurrentLowThresh;
    private String usbCPowerOutletConnectivity;
    private Double loadVoltage;
    private Boolean loadVoltageStatus;
    private Boolean loadCurrentStatus;
    private Double loadCurrent;
    private Boolean deviceStatus;
    private Boolean noiseLevelStatus;
}
