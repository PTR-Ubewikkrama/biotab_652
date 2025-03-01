package com.test_biotab.test_biotab_server.controller;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/biotab_e652/api/v1/test")
public class TestController {

    private final TestService testService;

    @Value("${application.security.test-add-hash-key}")
    private String hashKey;

    @PostMapping("/add/power-supply-test")
    public Mono<ResponseEntity<CommonResponse>> addPowerSupplyTest(@RequestBody PowerSupplyTestAddRequest powerSupplyTestAddRequest) {
        if (!powerSupplyTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(powerSupplyTestAddRequest.getHashKey());
        }
        log.info("Request received to add power supply test: {}", powerSupplyTestAddRequest);
        return testService.addPowerSupplyTest(powerSupplyTestAddRequest);
    }

    @PostMapping("/get/power-supply-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetPowerSupplyTestResponse>>> getPowerSupplyTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                     @RequestBody GetByPatternRequest request,
                                                                                     @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get power supply test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting power supply test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("POWER_SUPPLY");
                    return testService.getPowerSupplyTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/valve-test")
    public Mono<ResponseEntity<CommonResponse>> addValveTest(@RequestBody ValveTestAddRequest valveTestAddRequest) {
        if (!valveTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(valveTestAddRequest.getHashKey());
        }
        log.info("Request received to add valve test: {}", valveTestAddRequest);
        return testService.addValveTest(valveTestAddRequest);
    }

    @PostMapping("/get/valve-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetValveTestResponse>>> getValveTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                         @RequestBody GetByPatternRequest request,
                                                                         @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get valve test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting valve test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("VALVE");
                    return testService.getValveTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/air-pump-test")
    public Mono<ResponseEntity<CommonResponse>> addAirPumpTest(@RequestBody AirPumpTestAddRequest airPumpTestAddRequest) {
        if (!airPumpTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(airPumpTestAddRequest.getHashKey());
        }
        log.info("Request received to add air pump test: {}", airPumpTestAddRequest);
        return testService.addAirPumpTest(airPumpTestAddRequest);
    }

    @PostMapping("/get/air-pump-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetAirPumpTestResponse>>> getCustomer(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                          @RequestBody GetByPatternRequest request,
                                                                          @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get air pump test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting air pump test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("AIR_PUMP");
                    return testService.getAirPumpTest(request, userDetails, pageNo);
                });
    }

    private static Mono<ResponseEntity<CommonResponse>> sendInvalidResponse(String valueTestAddRequest) {
        log.error("Invalid hash key received: {}", valueTestAddRequest);
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.builder()
                        .message("Not permitted")
                        .status("401")
                        .build()));
    }
}
