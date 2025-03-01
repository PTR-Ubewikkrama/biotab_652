package com.test_jig.test_jig_server.domain;

import com.test_jig.test_jig_server.dto.LatchButtonTestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetLatchButtonTestResponse {
    private List<LatchButtonTestDto> latchButtonTests;
    private long totalRecords;
    private long totalFailed;
}
