package com.test_biotab.test_biotab_server.domain;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
