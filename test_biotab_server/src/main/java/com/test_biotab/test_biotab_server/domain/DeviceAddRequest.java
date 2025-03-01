package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class DeviceAddRequest {
    private String deviceType;
    private String deviceName;
    private String deviceMac;
}
