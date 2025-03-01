package com.test_jig.test_jig_server.controller;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.domain.GetByPatternRequest;
import com.test_jig.test_jig_server.domain.fa.ValidateDeviceForStageTwoResponse;
import com.test_jig.test_jig_server.domain.ValidateRequest;
import com.test_jig.test_jig_server.domain.fa.*;
import com.test_jig.test_jig_server.dto.fa.FinalAssemblyDto;
import com.test_jig.test_jig_server.service.FinalAssemblyService;
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
@RequestMapping("/wave_tech/api/v1/fa")
public class FinalAssemblyController {
    private final FinalAssemblyService finalAssemblyService;

    @PostMapping("/validate/device-id")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateDeviceId(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                    @RequestBody ValidateRequest request) {
        log.info("Received request to validate device id: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating device id: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateDeviceId(request, userDetails);
                });
    }

    @PostMapping("/validate/for-stage-two/device-id")
    public Mono<ResponseEntity<ApiResponse<ValidateDeviceForStageTwoResponse>>> validateDeviceIdForStageTwo(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                            @RequestBody ValidateRequest request) {
        log.info("Received request to validate device id for stage two: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating device id for stage two: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateDeviceIdForStageTwo(request, userDetails);
                });
    }

    @GetMapping("/get/{deviceId}")
    public Mono<ResponseEntity<ApiResponse<FinalAssemblyDto>>> getFinalAssemblyById(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                    @PathVariable("deviceId") String deviceId) {
        log.info("Received request to get Final Assembly by device id: {}", deviceId);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting Final Assembly by device id: {} by user: {}", deviceId, userDetails.getUsername());
                    return finalAssemblyService.getFinalAssemblyById(deviceId, userDetails);
                });
    }

    @PostMapping("/add/step01")
    public Mono<ResponseEntity<ApiResponse<Void>>> addFinalAssembly(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                    @RequestBody FAStepOneAddRequest request) {
        log.info("Received request to add Final Assembly step 01: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding Final Assembly 01: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.addFAStepOne(request, userDetails);
                });
    }

    @PostMapping("/update/step01")
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFinalAssembly(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                       @RequestBody FAStepOneUpdateRequest request) {
        log.info("Received request to update Final Assembly step 01: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Updating Final Assembly 01: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.updateFAStepOne(request, userDetails);
                });
    }

    @PostMapping("/validate/bladder")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateBladder(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                   @RequestBody ValidateRequest request) {
        log.info("Received request to validate bladder: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating bladder: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateBladder(request, userDetails);
                });
    }

    @PostMapping("/validate/upl-number")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateUplNumber(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                     @RequestBody ValidateRequest request) {
        log.info("Received request to validate upl number: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating upl number: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateUplNumber(request, userDetails);
                });
    }

    @PostMapping("/add/step02")
    public Mono<ResponseEntity<ApiResponse<Void>>> addFinalAssembly(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                    @RequestBody FAStepTwoAddRequest request) {
        log.info("Received request to add Final Assembly step 02: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding Final Assembly 02: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.addFAStepTwo(request, userDetails);
                });
    }

    @PostMapping("/update/step02")
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFinalAssembly(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                       @RequestBody FAStepTwoUpdateRequest request) {
        log.info("Received request to update Final Assembly step 02: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Updating Final Assembly 02: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.updateFAStepTwo(request, userDetails);
                });
    }

    @PostMapping("/validate/for-step-three/upl")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateUplForStepThree(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                           @RequestBody ValidateRequest request) {
        log.info("Received request to validate upl for step three: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating upl for step three: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateUplForStepThree(request, userDetails);
                });
    }

    @PostMapping("/validate/udi")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateUdi(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                               @RequestBody ValidateRequest request) {
        log.info("Received request to validate udi: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating udi: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateUdi(request, userDetails);
                });
    }

    @PostMapping("/validate/adapter")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateAdapter(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                   @RequestBody ValidateRequest request) {
        log.info("Received request to validate adapter: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating adapter: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateAdapter(request, userDetails);
                });
    }

    @PostMapping("/add/step03")
    public Mono<ResponseEntity<ApiResponse<Void>>> addFinalAssembly(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                    @RequestBody FAStepThreeAddRequest request) {
        log.info("Received request to add Final Assembly step 03: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding Final Assembly 03: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.addFAStepThree(request, userDetails);
                });
    }

    @PostMapping("/update/step03")
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFinalAssembly(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                       @RequestBody FAStepThreeUpdateRequest request) {
        log.info("Received request to update Final Assembly step 03: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Updating Final Assembly 03: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.updateFAStepThree(request, userDetails);
                });
    }

    @PostMapping("/validate/cartoon-package/udi")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateCartoonPackageUdi(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                             @RequestBody ValidateRequest request) {
        log.info("Received request to validate cartoon package udi: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating cartoon package udi: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateCartoonPackageUdi(request, userDetails);
                });
    }

    @PostMapping("/validate/cartoon-package/cartoon-number")
    public Mono<ResponseEntity<ApiResponse<Void>>> validateCartoonPackageCartoonNumber(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                       @RequestBody ValidateRequest request) {
        log.info("Received request to validate cartoon package cartoon number: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Validating cartoon package cartoon number: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.validateCartoonPackageCartoonNumber(request, userDetails);
                });
    }

    @PostMapping("/add/cartoon-package")
    public Mono<ResponseEntity<ApiResponse<Void>>> addFinalAssemblyCP(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                      @RequestBody FACartoonPackageAddRequest request) {
        log.info("Received request to add Final Assembly cartoon package: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Adding Final Assembly CartoonPackage: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.addFACartoonPackage(request, userDetails);
                });
    }

    @PostMapping("/update/cartoon-package")
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFinalAssemblyCP(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                         @RequestBody FACartoonPackageUpdateRequest request) {
        log.info("Received request to update Final Assembly cartoon package: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Updating Final Assembly CartoonPackage: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.updateFACartoonPackage(request, userDetails);
                });
    }

    @GetMapping("/get/cartoon-package/{udi}")
    public Mono<ResponseEntity<ApiResponse<GetFinalAssemblyByIdResponse>>> getFinalAssemblyByCartoonPackageUdi(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                               @PathVariable("udi") String udi) {
        log.info("Received request to get Final Assembly by cartoon package udi: {}", udi);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting Final Assembly by cartoon package udi: {} by user: {}", udi, userDetails.getUsername());
                    return finalAssemblyService.getFinalAssemblyByCartoonPackageUdi(udi, userDetails);
                });
    }

    @PostMapping("/get/{pageNo}")
    private Mono<ResponseEntity<ApiResponse<GetFinalAssemblyResponse>>> getFAs(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                               @RequestBody GetByPatternRequest request,
                                                                               @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get Final Assemblies by pattern: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting Final Assemblies by pattern: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.getFinalAssemblies(request, userDetails, pageNo);
                });
    }

    @PostMapping("/get/cartoon-package/by-id")
    private Mono<ResponseEntity<ApiResponse<GetCBByIdResponse>>> getCartoonBoxById(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                   @RequestBody ValidateRequest request) {
        log.info("Received request to get CB by pattern: {}", request);
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting CB by pattern: {} by user: {}", request, userDetails.getUsername());
                    return finalAssemblyService.getCartoonBoxById(request, userDetails);
                });
    }
}
