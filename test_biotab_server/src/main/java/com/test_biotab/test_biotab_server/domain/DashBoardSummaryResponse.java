package com.test_biotab.test_biotab_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardSummaryResponse {
    long totalSuccessAirPumpTest;
    long totalFailedAirPumpTest;
    long totalSuccessPowerSupplyTest;
    long totalFailedPowerSupplyTest;
    long totalSuccessValueTest;
    long totalFailedValueTest;
    long totalSuccessPcbTest;
    long totalFailedPcbTest;
    long totalSuccessPowerPcbTest;
    long totalFailedPowerPcbTest;
    long totalFinalAssembly;
    long totalHHDevice;
}
