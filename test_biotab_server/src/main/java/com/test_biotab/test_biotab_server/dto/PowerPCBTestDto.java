package com.test_biotab.test_biotab_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PowerPCBTestDto {
    private int testId;
    private int deviceId;
    private Double powerGroundResistanceUpperLimit;
    private String serialNumber;
    private Boolean dcBarrelJackConnectivityStatus;
    private Boolean usbCPowerOutletConnectivity;
    private Double powerGroundResistance;
    private Boolean powerGroundResistanceStatus;
    private Boolean status;
    private LocalDateTime dateTime;
}
