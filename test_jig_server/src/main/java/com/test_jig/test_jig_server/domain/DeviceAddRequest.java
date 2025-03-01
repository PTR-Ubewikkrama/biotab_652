package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class DeviceAddRequest {
    private String deviceType;
    private String deviceName;
    private String deviceMac;
}
