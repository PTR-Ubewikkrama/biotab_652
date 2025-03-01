package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class PowerSupplyTestAddRequest {
    private String hashKey;

    private String deviceMac;

    private Double idleVolLowTh;

    private Double idleVolUpTh;

    private Double loadVolLowTh;

    private Double loadVolUpTh;

    private Double loadCurUpTh;

    private String serialNumber;

    private Double idleVol;

    private Boolean idleVolStatus;

    private Double loadVol;

    private Boolean loadVolStatus;

    private Double loadCurrent;

    private Boolean loadCurrentStatus;

    private Double operatingPower;

    private Boolean noiseLevel;

    private String deviceStatus;
}
