package com.test_jig.test_jig_server.domain.fa;

import lombok.Data;

@Data
public class FAStepTwoUpdateRequest {
    private String deviceCode;
    private String bladderCode;
    private String uplNumber;
}
