package com.test_biotab.test_biotab_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String email;
    private String name;
    private String group;
    private String status;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
}
