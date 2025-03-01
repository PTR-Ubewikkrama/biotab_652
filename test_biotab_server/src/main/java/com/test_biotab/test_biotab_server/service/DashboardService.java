package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.ApiResponse;
import com.test_biotab.test_biotab_server.domain.DashBoardSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface DashboardService {
    Mono<ResponseEntity<ApiResponse<DashBoardSummaryResponse>>> getSummary(UserDetails userDetails);
}