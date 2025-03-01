package com.test_biotab.test_biotab_server.domain.fa;

import lombok.Data;

@Data
public class FAStepTwoAddRequest {
    private String deviceCode;
    private String bladderCode;
    private String uplNumber;
}
