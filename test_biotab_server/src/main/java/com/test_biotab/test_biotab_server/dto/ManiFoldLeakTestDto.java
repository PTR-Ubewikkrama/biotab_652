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
public class ManiFoldLeakTestDto {
    private int testId;
    private String deviceMac;
    private String location;
    private String serialNumber;
    private Boolean physicalInspectionState;
    private Double leakageFlowrate;
    private Boolean manifoldLeakState;
    private Boolean overallManifoldLeakState;
    private Boolean status;
    private LocalDateTime dateTime;
}
