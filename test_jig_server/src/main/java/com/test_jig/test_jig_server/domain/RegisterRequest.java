package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String userType;
}
