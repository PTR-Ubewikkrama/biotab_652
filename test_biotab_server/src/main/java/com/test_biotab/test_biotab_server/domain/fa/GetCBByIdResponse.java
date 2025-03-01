package com.test_biotab.test_biotab_server.domain.fa;

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
