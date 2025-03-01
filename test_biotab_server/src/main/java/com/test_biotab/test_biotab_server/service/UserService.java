package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByEmail(String email);

    Mono<ResponseEntity<ApiResponse<AuthResponse>>> login(LoginRequest loginRequest);

    Mono<ResponseEntity<ApiResponse<Void>>> register(RegisterRequest registerRequest, UserDetails principal);

    Mono<? extends ResponseEntity<ApiResponse<GetUsersResponse>>> getUsers(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<ApiResponse<Void>>> resetPassword(ResetPasswordRequest resetPasswordRequest, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<AuthResponse>>> refresh(UserDetails userDetails);
}
