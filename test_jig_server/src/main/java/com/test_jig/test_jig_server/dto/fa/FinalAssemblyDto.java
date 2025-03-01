package com.test_jig.test_jig_server.dto.fa;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalAssemblyDto {
    private Integer id;
    private String deviceCode;
    private String category;
    private String bladderCode;
    private String uplNumber;
    private String udiNumber;
    private String adapterCode;
    private String cartoonNumber;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
    private String createdBy;
}
