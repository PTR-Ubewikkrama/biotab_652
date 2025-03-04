package com.test_biotab.test_biotab_server.controller;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.BTDeviceDto;
import com.test_biotab.test_biotab_server.service.BTDeviceService;
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
@RequestMapping("/biotab_e652/api/v1/bt_device")
public class BTDeviceController {

    private final BTDeviceService service;

    @PostMapping("/add")
    public Mono<ResponseEntity<ApiResponse<Void>>> addDevice(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                             @RequestBody BTDeviceAddRequest request) {
        log.info("Received request to add BT device: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding BT device: {} by user: {}", request, userDetails.getUsername());
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
    private Mono<ResponseEntity<ApiResponse<GetBTDevicesResponse>>> getDevices(@AuthenticationPrincipal Mono<UserDetails> principal,
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
    private Mono<ResponseEntity<ApiResponse<BTDeviceDto>>> getDeviceByCode(@AuthenticationPrincipal Mono<UserDetails> principal,
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
                    return service.validateBTDevice(request, userDetails);
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

    @PostMapping("/validate/fanTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateFanTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                            @RequestBody ValidateRequest request) {
        log.info("Received request to validate Fan test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Fan test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateFanTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/uiPcbTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateUiPcbTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                              @RequestBody ValidateRequest request) {
        log.info("Received request to validate UI PCB test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating UI PCB test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateUiPcbTestCode(request, userDetails);
                });
    }

    @PostMapping("/validate/manifoldTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateManifoldTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                 @RequestBody ValidateRequest request) {
        log.info("Received request to validate Manifold test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Manifold test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateManifoldTestCode(request, userDetails);
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

    @PostMapping("/validate/valveCardTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                              @RequestBody ValidateRequest request) {
        log.info("Received request to validate Card Valve test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Valve Card test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateValveCardTestCode(request, userDetails);
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

    @PostMapping("/validate/valveSequenceTestCode")
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveSequenceTestCode(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                          @RequestBody ValidateRequest request) {
        log.info("Received request to validate Valve Sequence test code: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating Valve Sequence test code: {} by user: {}", request, userDetails.getUsername());
                    return service.validateValveSequenceTestCode(request, userDetails);
                });
    }
}
