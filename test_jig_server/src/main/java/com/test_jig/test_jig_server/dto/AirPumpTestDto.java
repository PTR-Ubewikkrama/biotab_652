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
public class AirPumpTestDto {
    private int testId;

    private int deviceId;

    private Double idleVolLowTh;

    private Double idleVolUpTh;

    private Double idleCurUpTh;

    private Double loadVolLowTh;

    private Double loadVolUp;

    private Double loadCurUpTh;

    private Double setPressure;

    private String serialNumber;

    private Double idleVol;

    private Boolean idleVolStatus;

    private Double idleCurrent;

    private Boolean idleCurrentStatus;

    private Double loadVoltage;

    private Boolean loadVoltageStatus;

    private Double loadCurrent;

    private Boolean loadCurrentStatus;

    private Double maxPressure;

    private Boolean maxPressureStatus;

    private Boolean noiseLevel;

    private LocalDateTime dateTime;

    private Boolean status;

    private String deviceStatus;
}
