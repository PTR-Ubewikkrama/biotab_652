package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class OverPressureValveTestAddRequest {
    private String hashKey;

    private String deviceMac;

    private String qrCode;

    private Double maxPressure;

    private Double maxPressureTime;

    private Double maxPressureFlowRate;

    private Double normalPressure;

    private Double normalPressureTime;

    private Double normalPressureFlowRate;

    private Boolean overPressureValveStatus;
}
