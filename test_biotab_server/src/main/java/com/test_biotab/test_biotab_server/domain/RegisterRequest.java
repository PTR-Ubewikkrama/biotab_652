package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String userType;
}
