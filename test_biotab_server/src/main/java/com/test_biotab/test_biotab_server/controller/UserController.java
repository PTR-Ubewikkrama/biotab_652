package com.test_biotab.test_biotab_server.controller;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/biotab_e652/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<ApiResponse<AuthResponse>>> login(@RequestBody LoginRequest loginRequest) {
        log.info("Received request to login: {}", loginRequest.getEmail());
        return userService.login(loginRequest);
    }

    @GetMapping("/validate")
    public Mono<ResponseEntity<ApiResponse<AuthResponse>>> validate(@AuthenticationPrincipal Mono<UserDetails> principal) {
        log.info("Received request to validate user");
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating user: {}", userDetails.getUsername());
                    return userService.refresh(userDetails);
                });
    }

    @PostMapping("/user/reset-password")
    public Mono<ResponseEntity<ApiResponse<Void>>> resetPassword(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                 @RequestBody ResetPasswordRequest resetPasswordRequest) {
        log.info("Received request to reset password for: [{}]", resetPasswordRequest.getEmail());
        return principal
                .flatMap(userDetails -> {
                    log.info("Resetting password for: [{}] by user: [{}]", resetPasswordRequest.getEmail(), userDetails.getUsername());
                    return userService.resetPassword(resetPasswordRequest, userDetails);
                });
    }

    @PostMapping("/user/add-user")
    public Mono<ResponseEntity<ApiResponse<Void>>> addUser(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                           @RequestBody RegisterRequest registerRequest) {
        log.info("Received request to register: [{}]", registerRequest.getEmail());
        return principal
                .flatMap(userDetails -> {
                    log.info("Registering user: [{}] by user [{}]", registerRequest.getEmail(), userDetails.getUsername());
                    return userService.register(registerRequest, userDetails);
                });
    }

    @PostMapping("/user/get/{pageNo}")
    private Mono<ResponseEntity<ApiResponse<GetUsersResponse>>> getUsers(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                         @RequestBody GetByPatternRequest request,
                                                                         @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get users with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting users with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    return userService.getUsers(request, userDetails, pageNo);
                });
    }
}
