package com.test_jig.test_jig_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private int id;
    private String deviceType;
    private String deviceName;
    private String deviceMac;
    private String createdAt;
}
