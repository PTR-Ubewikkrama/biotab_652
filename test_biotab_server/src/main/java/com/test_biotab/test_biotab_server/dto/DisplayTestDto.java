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
public class DisplayTestDto {
    private int testId;
    private int deviceId;
    private String serialNumber;
    private Boolean physicalInspectionState;
    private Boolean backLightOn;
    private Boolean redScreenOn;
    private Boolean greenScreenOn;
    private Boolean blueScreenOn;
    private Boolean colorPatch;
    private Boolean BTDisplayText;
    private Boolean screenOff;
    private Boolean overallDisplayStatus;
    private Boolean status;
    private LocalDateTime dateTime;
}
