package com.test_jig.test_jig_server.domain.fa;

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
public class GetCBByIdResponse {
    private List<String> uids;
    private String cartoonNumber;
}
