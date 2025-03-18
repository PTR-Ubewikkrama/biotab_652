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
public class MainPCBTestUnitDto {
    private String testName;
    private String testType;
    private String validationType;
    private String actualValue;
    private double minValue;
    private double maxValue;
    private String unit;
    private boolean status;
    private LocalDateTime dateTime;
}
