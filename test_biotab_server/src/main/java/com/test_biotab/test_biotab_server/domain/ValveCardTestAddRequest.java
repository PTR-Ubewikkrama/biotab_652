package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class ValveCardTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Boolean physicalInspectionState;
    private String rail;
    private String valve1;
    private String valve3;
    private String valve5;
    private String valve7;
    private String valve2;
    private String valve4;
    private String valve6;
    private String valve8;
    private String amperageTest;
    private String shiftRegisterTest;
    private String overallValveCardState;
}
