package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class ValveTestAddRequest {
    private String hashKey;

    private String deviceMac;

    private String qrCode;

    private double airChamberLoadingPressure;

    private boolean airChamberStatus;

    private double v1OutletPressureAfter10MsOnTime;

    private boolean v1OutletOnStatus;

    private double v1OutletPressureAfter10MsOffTime;

    private boolean v1OutletOffStatus;

    private double v2OutletPressureAfter10MsOnTime;

    private boolean v2OutletOnStatus;

    private double v2OutletPressureAfter10MsOffTime;

    private boolean v2OutletOffStatus;

    private double v3OutletPressureAfter10MsOnTime;

    private boolean v3OutletOnStatus;

    private double v3OutletPressureAfter10MsOffTime;

    private boolean v3OutletOffStatus;

    private boolean valveStatus;
}
