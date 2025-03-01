package com.test_biotab.test_biotab_server.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse {
    private String message;
    private String status;
}
