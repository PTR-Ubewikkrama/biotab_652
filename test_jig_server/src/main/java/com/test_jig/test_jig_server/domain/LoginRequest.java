package com.test_jig.test_jig_server.domain;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
