package com.test_biotab.test_biotab_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTestResponse<T> {
    private List<T> tests;
    private long totalRecords;
    private long totalFailed;
}
