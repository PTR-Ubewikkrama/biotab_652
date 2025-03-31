package com.test_biotab.test_biotab_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPCBTestDto {
    private Integer id;
    private String deviceMac;
    private String serialNumber;
    private String softwareVersion;
    private String batchNumber;
    private String testId;
    private boolean status;
    private List<MainPCBTestUnitDto> testResultData;
    private LocalDateTime dateTime;
}
