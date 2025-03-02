package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class CableTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Integer cableSelection;
    private Boolean visualInspection;
    private Boolean cable1;
    private Boolean cable2;
    private Boolean cable3;
    private Boolean cable4;
    private Boolean cable5;
    private Boolean cable6;
    private Boolean cable7;
    private Boolean cable8;
    private Boolean cable9;
    private Boolean cable10;
    private Boolean overallCableState;
}
