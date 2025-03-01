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
@RequestMapping("/wave_tech/api/v1/test")
public class TestController {

    private final TestService testService;

    @Value("${application.security.test-add-hash-key}")
    private String hashKey;

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

    @PostMapping("/add/battery-test")
    public Mono<ResponseEntity<CommonResponse>> addValueTest(@RequestBody BatteryTestAddRequest batteryTestAddRequest) {
        if (!batteryTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(batteryTestAddRequest.getHashKey());
        }
        log.info("Request received to add battery test: {}", batteryTestAddRequest);
        return testService.addBatteryTest(batteryTestAddRequest);
    }

    private static Mono<ResponseEntity<CommonResponse>> sendInvalidResponse(String valueTestAddRequest) {
        log.error("Invalid hash key received: {}", valueTestAddRequest);
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.builder()
                        .message("Not permitted")
                        .status("401")
                        .build()));
    }

    @PostMapping("/get/battery-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetBatteryTestResponse>>> getBatteryTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                             @RequestBody GetByPatternRequest request,
                                                                             @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get battery test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting value test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("VALVE");
                    return testService.getBatteryTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/over-pressure-test")
    public Mono<ResponseEntity<CommonResponse>> addOverPressureTest(@RequestBody OverPressureValveTestAddRequest overPressureTestAddRequest) {
        if (!overPressureTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(overPressureTestAddRequest.getHashKey());
        }
        log.info("Request received to add over pressure test: {}", overPressureTestAddRequest);
        return testService.addOverPressureTest(overPressureTestAddRequest);
    }

    @PostMapping("/get/over-pressure-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetOverPressureValveTestResponse>>> getOverPressureTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                            @RequestBody GetByPatternRequest request,
                                                                                            @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get over pressure test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting over pressure test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("OVER_PRESSURE");
                    return testService.getOverPressureTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/latch-button-test")
    public Mono<ResponseEntity<CommonResponse>> addLatchButtonTest(@RequestBody LatchButtonAddRequest latchButtonAddRequest) {
        if (!latchButtonAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(latchButtonAddRequest.getHashKey());
        }
        log.info("Request received to add latch button test: {}", latchButtonAddRequest);
        return testService.addLatchButtonTest(latchButtonAddRequest);
    }

    @PostMapping("/get/latch-button-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetLatchButtonTestResponse>>> getLatchButtonTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                     @RequestBody GetByPatternRequest request,
                                                                                     @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get latch button test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting latch button test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("LATCH_BUTTON");
                    return testService.getLatchButtonTest(request, userDetails, pageNo);
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

    @PostMapping("/add/pcb-test")
    public Mono<ResponseEntity<CommonResponse>> addPcbTest(@RequestBody PcbTestAddRequest pcbTestAddRequest) {
        log.info("Request received to add pcb test: {}", pcbTestAddRequest);
        if (!pcbTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(pcbTestAddRequest.getHashKey());
        }
        return testService.addPcbTest(pcbTestAddRequest);
    }

    @PostMapping("/get/pcb-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetPcbTestResponse>>> getPcbTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                     @RequestBody GetByPatternRequest request,
                                                                     @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get pcb test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting pcb test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("PCB");
                    return testService.getPcbTest(request, userDetails, pageNo);
                });
    }
}
