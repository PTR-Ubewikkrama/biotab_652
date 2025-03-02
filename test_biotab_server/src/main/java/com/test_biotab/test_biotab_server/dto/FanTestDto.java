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
public class FanTestDto {
    private int testId;
    private int deviceId;
    private String qrCode;
    private Boolean visualInspection;
    private Double drawCurrent;
    private Boolean drawCurrentState;
    private Double fanSpeed;
    private Boolean fanSpeedState;
    private Boolean overallFanState;
    private Boolean status;
    private LocalDateTime dateTime;
}
