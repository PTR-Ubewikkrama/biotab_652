package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class FanTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private String visualInspection;
    private Double drawCurrent;
    private Boolean drawCurrentState;
    private Double fanSpeed;
    private Boolean fanSpeedState;
    private Boolean overallFanState;
}
