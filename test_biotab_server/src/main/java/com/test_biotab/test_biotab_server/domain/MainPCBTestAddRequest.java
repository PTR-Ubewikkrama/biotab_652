package com.test_biotab.test_biotab_server.domain;

import com.test_biotab.test_biotab_server.dto.MainPCBTestUnitDto;
import lombok.Data;

import java.util.List;

@Data
public class MainPCBTestAddRequest {
    private String hashKey;
    private String deviceMac;
    private String serialNumber;
    private String softwareVersion;
    private String batchNumber;
    private String testId;
    private boolean status;
    List<MainPCBTestUnitDto> testResultData;
}
