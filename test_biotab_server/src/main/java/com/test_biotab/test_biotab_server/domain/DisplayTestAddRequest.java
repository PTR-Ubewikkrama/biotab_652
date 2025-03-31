package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class DisplayTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Boolean physicalinspectionstate;
    private Boolean backlighton;
    private Boolean redscreenon;
    private Boolean greenscreenon;
    private Boolean bluescreenon;
    private Boolean colorpatch;
    private Boolean btdisplaytext;
    private Boolean screenoff;
    private Boolean overallDisplayStatus;
}
