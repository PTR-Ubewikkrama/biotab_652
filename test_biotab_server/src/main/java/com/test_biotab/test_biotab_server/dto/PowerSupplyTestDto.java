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
public class PowerSupplyTestDto {
    private Integer testId;

    private String deviceMac;

    private Double idleVolLowTh;

    private Double idleVolUpTh;

    private Double loadVolLowTh;

    private Double loadVolUpTh;

    private Double loadCurUpTh;

    private String serialNumber;

    private Double idleVol;

    private Boolean idleVolStatus;

    private Double loadVol;

    private Boolean loadVolStatus;

    private Double loadCurrent;

    private Boolean loadCurrentStatus;

    private Double operatingPower;

    private Boolean noiseLevel;

    private String deviceStatus;

    private Boolean status;

    private LocalDateTime dateTime;
}
