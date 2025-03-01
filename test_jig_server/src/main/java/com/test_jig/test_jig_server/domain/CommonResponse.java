package com.test_jig.test_jig_server.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse {
    private String message;
    private String status;
}
