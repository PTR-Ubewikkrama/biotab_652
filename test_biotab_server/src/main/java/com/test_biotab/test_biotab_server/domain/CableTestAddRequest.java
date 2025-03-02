package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class CableTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Integer cableSelection;
    private String visualInspection;
    private String cable1;
    private String cable2;
    private String cable3;
    private String cable4;
    private String cable5;
    private String cable6;
    private String cable7;
    private String cable8;
    private String cable9;
    private String cable10;
    private String overallCableState;
}
