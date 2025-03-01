package com.test_jig.test_jig_server.controller;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.domain.DeviceAddRequest;
import com.test_jig.test_jig_server.domain.GetByPatternRequest;
import com.test_jig.test_jig_server.domain.GetDevicesResponse;
import com.test_jig.test_jig_server.service.DeviceService;
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
@RequestMapping("/wave_tech/api/v1/device")
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/add")
    public Mono<ResponseEntity<ApiResponse<Void>>> addDevice(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                             @RequestBody DeviceAddRequest request) {
        log.info("Received request to add device: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding device: {} by user: {}", request, userDetails.getUsername());
                    return deviceService.addDevice(request, userDetails);
                });
    }

    @PostMapping("/get/{pageNo}")
    private Mono<ResponseEntity<ApiResponse<GetDevicesResponse>>> getDevices(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                             @RequestBody GetByPatternRequest request,
                                                                             @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get devices by pattern: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting devices by pattern: {} by user: {}", request, userDetails.getUsername());
                    return deviceService.getDevices(request, userDetails, pageNo);
                });
    }
}
