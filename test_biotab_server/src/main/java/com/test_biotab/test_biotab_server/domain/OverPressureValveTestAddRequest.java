package com.test_biotab.test_biotab_server.domain;

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
