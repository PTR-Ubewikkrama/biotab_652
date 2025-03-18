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
    long totalSuccessAirPumpV2Test;
    long totalFailedAirPumpV2Test;
    long totalSuccessPowerSupplyV2Test;
    long totalFailedPowerSupplyV2Test;
    long totalSuccessPowerPcbV2Test;
    long totalFailedPowerPcbV2Test;
    long totalSuccessOpValveTest;
    long totalFailedOpValveTest;
    long totalSuccessValveSequenceTest;
    long totalFailedValveSequenceTest;
    long totalSuccessValveCardTest;
    long totalFailedValveCardTest;
    long totalSuccessManiFoldLeakTest;
    long totalFailedManiFoldLeakTest;
    long totalSuccessUiPcbTest;
    long totalFailedUiPcbTest;
    long totalSuccessCableTest;
    long totalFailedCableTest;
    long totalSuccessFanTest;
    long totalFailedFanTest;
    long totalSuccessDisplayTest;
    long totalFailedDisplayTest;
    long totalFinalAssembly;
    long totalHHDevice;
}
