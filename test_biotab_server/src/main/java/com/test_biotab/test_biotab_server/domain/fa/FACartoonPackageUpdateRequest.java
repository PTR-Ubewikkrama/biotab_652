package com.test_biotab.test_biotab_server.domain.fa;

import lombok.Data;

import java.util.List;

@Data
public class FACartoonPackageUpdateRequest {
    private int id;
    private String cartoonNumber;
    private List<String> udiNumbers;
}
