package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class ManiFoldLeakTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String qrCode;
    private Boolean physicalInspectionState;
    private Double leakageFlowrate;
    private Boolean manifoldLeakState;
    private Boolean overallManifoldLeakState;
}
