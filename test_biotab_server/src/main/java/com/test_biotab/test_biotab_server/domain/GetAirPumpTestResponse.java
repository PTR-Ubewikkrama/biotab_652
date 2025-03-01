package com.test_biotab.test_biotab_server.domain;

import com.test_biotab.test_biotab_server.dto.AirPumpTestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAirPumpTestResponse {
    private List<AirPumpTestDto> airPumpTests;
    private long totalRecords;
    private long totalFailed;
}
