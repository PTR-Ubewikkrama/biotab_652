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
public class ValveCardTestDto {
    private int testId;
    private int deviceId;
    private String serialNumber;
    private Boolean physicalInspectionState;
    private String rail;
    private String valve1;
    private String valve3;
    private String valve5;
    private String valve7;
    private String valve2;
    private String valve4;
    private String valve6;
    private String valve8;
    private String amperageTest;
    private String shiftRegisterTest;
    private String overallValveCardState;
    private Boolean status;
    private LocalDateTime dateTime;
}
