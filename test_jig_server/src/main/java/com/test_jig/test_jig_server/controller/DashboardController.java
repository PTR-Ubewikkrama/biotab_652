package com.test_jig.test_jig_server.controller;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.service.DashboardService;
import com.test_jig.test_jig_server.domain.DashBoardSummaryResponse;
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
@RequestMapping("/wave_tech/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/get/summary")
    public Mono<ResponseEntity<ApiResponse<DashBoardSummaryResponse>>> addDevice(@AuthenticationPrincipal Mono<UserDetails> principal) {
        log.info("Received request to get summary");
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting summary by user: {}", userDetails.getUsername());
                    return dashboardService.getSummary(userDetails);
                });
    }
}
