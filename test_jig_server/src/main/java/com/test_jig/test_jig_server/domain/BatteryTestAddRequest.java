package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class BatteryTestAddRequest {
    private String hashKey;

    private String deviceMac;

    private String qrCode;

    private Double maximumCurrent;

    private Double maxCurrentDrawnTime;

    private boolean maxCurrentCutOff;

    private Double normalCurrent;

    private Double normalCurrentDrawnTime;

    private boolean normalCurrentCutOff;

    private Boolean batteryStatus;
}
