package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class GetByPatternRequest {
    private String filterType;
    private String filterValue;
    private String status;
    private String fromDate;
    private String toDate;
    private String requestType;
}
