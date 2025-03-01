package com.test_biotab.test_biotab_server.domain.fa;

import com.test_biotab.test_biotab_server.dto.fa.FinalAssemblyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFinalAssemblyResponse {
    private List<FinalAssemblyDto> fas;
    private long total;
}
