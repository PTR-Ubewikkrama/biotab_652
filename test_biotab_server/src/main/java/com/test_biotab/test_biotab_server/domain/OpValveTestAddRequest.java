package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class OpValveTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Boolean physicalInspectionState;
    private Double startOpeningPressure;
    private Double startOpeningFlowrate;
    private Boolean valveStartOpeningState;
    private Double fullyOpeningPressure;
    private Double fullyOpeningFlowrate;
    private Boolean valveFullyOpeningState;
    private Double closingPressure;
    private Double closingFlowrate;
    private Boolean valveClosingState;
    private Boolean overallOpValveState;
}
