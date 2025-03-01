package com.test_jig.test_jig_server.domain;

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
    long totalSuccessOverPressureTest;
    long totalFailedOverPressureTest;
    long totalSuccessLatchButtonTest;
    long totalFailedLatchButtonTest;
    long totalSuccessBatteryTest;
    long totalFailedBatteryTest;
    long totalFinalAssembly;
    long totalHHDevice;
}
