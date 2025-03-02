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
public class OpValveTestDto {
    private int testId;
    private int deviceId;
    private String qrCode;
    private Boolean physicalInspectionState;
    private Double startOpeningPressure;
    private Double startOpeningFlowrate;
    private Boolean valveStartOpeningState;
    private Double fullyOpeningPressure;
    private Double fullyOpeningFlowrate;
    private Boolean valveFullyOpeningState;
    private Double closingPressure;
    private Double closingFlowrate;
    private Boolean valveClosingState;
    private Boolean overallOpValveState;
    private Boolean status;
    private LocalDateTime dateTime;
}
