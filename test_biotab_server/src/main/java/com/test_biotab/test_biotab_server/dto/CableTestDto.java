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
public class CableTestDto {
    private int testId;
    private int deviceId;
    private String qrCode;
    private Integer cableSelection;
    private Boolean visualInspection;
    private Boolean cable1;
    private Boolean cable2;
    private Boolean cable3;
    private Boolean cable4;
    private Boolean cable5;
    private Boolean cable6;
    private Boolean cable7;
    private Boolean cable8;
    private Boolean cable9;
    private Boolean cable10;
    private Boolean overallCableState;
    private Boolean status;
    private LocalDateTime dateTime;
}
