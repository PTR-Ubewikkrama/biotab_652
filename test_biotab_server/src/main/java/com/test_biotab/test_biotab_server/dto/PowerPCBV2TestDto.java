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
public class PowerPCBV2TestDto {
    private int testId;
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
    private Boolean status;
    private LocalDateTime dateTime;
}
