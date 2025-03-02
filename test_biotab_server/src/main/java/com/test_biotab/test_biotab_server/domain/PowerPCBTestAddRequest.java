package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class PowerPCBTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private Double powerGroundResistanceUpperLimit;
    private String serialNumber;
    private Boolean dcBarrelJackConnectivityStatus;
    private Boolean usbCPowerOutletConnectivity;
    private Double powerGroundResistance;
    private Boolean powerGroundResistanceStatus;
}
