package com.test_jig.test_jig_server.controller;

import com.test_jig.test_jig_server.domain.*;
import com.test_jig.test_jig_server.dto.DeviceHHDto;
import com.test_jig.test_jig_server.service.HHDeviceService;
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
@RequestMapping("/wave_tech/api/v1/hh_device")
public class HHDeviceController {

    private final HHDeviceService service;

    @PostMapping("/add")
    public Mono<ResponseEntity<ApiResponse<Void>>> addDevice(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                             @RequestBody HHDeviceAddRequest request) {
        log.info("Received request to add HH device: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding HH device: {} by user: {}", request, userDetails.getUsername());
                    return service.add(request, userDetails);
                });
    }

    @PostMapping("/delete")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteDevice(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                @RequestBody ValidateRequest request) {
        log.info("Received request to delete HH device: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Deleting HH device: {} by user: {}", request, userDetails.getUsername());
                    return service.delete(request, userDetails);
                });
    }

    @PostMapping("/get/{pageNo}")
    private Mono<ResponseEntity<ApiResponse<GetHHDevicesResponse>>> getDevices(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                               @RequestBody GetByPatternRequest request,
                                                                               @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get HH devices by pattern: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting HH devices by pattern: {} by user: {}", request, userDetails.getUsername());
                    return service.getDevices(request, userDetails, pageNo);
                });
    }

    @PostMapping("/get/by_code")
    private Mono<ResponseEntity<ApiResponse<DeviceHHDto>>> getDeviceByCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                           @RequestBody ValidateRequest request) {
        log.info("Received request to get HH device by code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting HH device by code: {} by user: {}", request, userDetails.getUsername());
                    return service.getDeviceByCode(request, userDetails);
                });
    }

    @PostMapping("/validate")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateDevice(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                  @RequestBody ValidateRequest request) {
        log.info("Received request to validate HH device: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating HH device: {} by user: {}", request, userDetails.getUsername());
                    return service.validateHHDevice(request, userDetails);
                });
    }

    @PostMapping("/component/verification")
    public Mono<ResponseEntity<ApiResponse<ComponentVerificationResponse>>> verifyComponent(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                            @RequestBody ValidateRequest request) {
        log.info("Received request to verify component: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Verifying component: {} by user: {}", request, userDetails.getUsername());
                    return service.verifyComponent(request, userDetails);
                });
    }

    @PostMapping("/validate/pcbTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePcbTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                            @RequestBody ValidateRequest request) {
        log.info("Received request to validate PCB test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating PCB test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validatePcbTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/airPumpTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateAirPumpTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                @RequestBody ValidateRequest request) {
        log.info("Received request to validate Air Pump test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Air Pump test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateAirPumpTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/batteryTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateBatteryTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                @RequestBody ValidateRequest request) {
        log.info("Received request to validate Battery test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Battery test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateBatteryTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/latchButtonTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateLatchButtonTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                    @RequestBody ValidateRequest request) {
        log.info("Received request to validate Latch Button test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Latch Button test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateLatchButtonTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/overPressureValveTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateOverPressureValveTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                          @RequestBody ValidateRequest request) {
        log.info("Received request to validate Over Pressure Valve test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Over Pressure Valve test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateOverPressureValveTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/powerSupplyTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePowerSupplyTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                    @RequestBody ValidateRequest request) {
        log.info("Received request to validate Power Supply test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Power Supply test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validatePowerSupplyTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/valveTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                              @RequestBody ValidateRequest request) {
        log.info("Received request to validate Valve test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Valve test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateValveTestCode(request, userDetails);
                });
    }
}
