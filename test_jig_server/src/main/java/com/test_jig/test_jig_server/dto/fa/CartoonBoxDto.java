package com.test_jig.test_jig_server.dto.fa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartoonBoxDto {
    private Integer id;
    private String cartoonNumber;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
}
