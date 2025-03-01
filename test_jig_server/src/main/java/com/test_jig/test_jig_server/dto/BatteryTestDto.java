package com.test_jig.test_jig_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatteryTestDto {
    private Integer testId;

    private String deviceMac;

    private String qrCode;

    private Double maximumCurrent;

    private Double maxCurrentDrawnTime;

    private boolean maxCurrentCutOff;

    private Double normalCurrent;

    private Double normalCurrentDrawnTime;

    private boolean normalCurrentCutOff;

    private Boolean batteryStatus;

    private LocalDateTime dateTime;
}
