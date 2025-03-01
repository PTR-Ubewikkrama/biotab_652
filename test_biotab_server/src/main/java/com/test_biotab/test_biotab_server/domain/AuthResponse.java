package com.test_biotab.test_biotab_server.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String username;
    private String group;
    private List<String> roles;
    private String status;
}
