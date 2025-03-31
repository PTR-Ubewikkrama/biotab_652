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
public class UiPcbTestDto {
    private Integer testId;
    private String deviceMac;
    private String serialNumber;
    private Boolean physicalInspectionState;
    private Boolean redLedState;
    private Boolean whiteLedState;
    private Boolean ledRingOnState;
    private Boolean ledRingFadeState;
    private Boolean overallUiPcbState;
    private Boolean status;
    private LocalDateTime dateTime;
}
