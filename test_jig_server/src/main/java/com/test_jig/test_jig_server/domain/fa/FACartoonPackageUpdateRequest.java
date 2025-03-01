package com.test_jig.test_jig_server.domain.fa;

import lombok.Data;

import java.util.List;

@Data
public class FACartoonPackageUpdateRequest {
    private int id;
    private String cartoonNumber;
    private List<String> udiNumbers;
}
