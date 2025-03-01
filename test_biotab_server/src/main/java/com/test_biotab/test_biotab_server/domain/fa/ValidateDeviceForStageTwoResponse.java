package com.test_biotab.test_biotab_server.domain.fa;

import com.test_biotab.test_biotab_server.dto.fa.FinalAssemblyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateDeviceForStageTwoResponse {
    private FinalAssemblyDto finalAssembly;
}
