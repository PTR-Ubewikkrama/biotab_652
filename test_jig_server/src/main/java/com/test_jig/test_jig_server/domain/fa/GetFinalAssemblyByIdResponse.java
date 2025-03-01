package com.test_jig.test_jig_server.domain.fa;

import com.test_jig.test_jig_server.dto.DeviceHHDto;
import com.test_jig.test_jig_server.dto.fa.CartoonBoxDto;
import com.test_jig.test_jig_server.dto.fa.FinalAssemblyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFinalAssemblyByIdResponse {
    private FinalAssemblyDto finalAssembly;
    private CartoonBoxDto cartoonBox;
    private DeviceHHDto device;
}
