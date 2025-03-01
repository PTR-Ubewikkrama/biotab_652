package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class LatchButtonAddRequest {
    private String hashKey;

    private String qrCode;

    private String deviceMac;

    private Boolean buttonOnTestStatus;

    private Boolean buttonOffTestStatus;

    private Boolean ledOnTestStatus;

    private Boolean latchButtonStatus;
}
