package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class HHDeviceAddRequest {
    private String deviceCode;
    private String deviceCodeStatus;

    private String pcbTestCode;
    private String pcbTestCodeStatus;

    private String valveTestOneCode;
    private String valveTestOneCodeStatus;

    private String valveTestTwoCode;
    private String valveTestTwoCodeStatus;

    private String airPumpTestCode;
    private String airPumpTestCodeStatus;

    private String latchButtonTestCode;
    private String latchButtonTestCodeStatus;

    private String overPressureValveTestCode;
    private String overPressureValveTestCodeStatus;

    private String batteryTestCode;
    private String batteryTestCodeStatus;

    private String enclosureCode;
    private String enclosureCodeStatus;

    private String airBladderCode;
    private String airBladderCodeStatus;

    private String powerSupplyTestCode;
    private String powerSupplyTestCodeStatus;
}
