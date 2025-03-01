package com.test_jig.test_jig_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComponentVerificationResponse {
    private String componentCode;
    private String componentType;
    private String componentStatus;
    private String hhDeviceCode;
}
