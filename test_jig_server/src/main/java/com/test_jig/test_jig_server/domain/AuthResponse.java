package com.test_jig.test_jig_server.domain;

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
