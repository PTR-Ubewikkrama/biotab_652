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
public class OverPressureValveTestDto {
    private Integer testId;

    private String deviceMac;

    private String qrCode;

    private Double maxPressure;

    private Double maxPressureTime;

    private Double maxPressureFlowRate;

    private Double normalPressure;

    private Double normalPressureTime;

    private Double normalPressureFlowRate;

    private Boolean overPressureValveStatus;

    private Boolean status;

    private LocalDateTime dateTime;
}
