package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class UiPcbTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Boolean physicalInspectionState;
    private Boolean redLedState;
    private Boolean whiteLedState;
    private Boolean ledRingOnState;
    private Boolean ledRingFadeState;
    private Boolean overallUiPcbState;
}
