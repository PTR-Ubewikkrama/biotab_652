package com.test_biotab.test_biotab_server.controller;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.*;
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
    Mono<ResponseEntity<ApiResponse<GetTestResponse<PowerSupplyTestDto>>>> getPowerSupplyTest(@AuthenticationPrincipal Mono<UserDetails> principal, @RequestBody GetByPatternRequest request, @PathVariable("pageNo") String pageNo) {
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
    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveTestDto>>>> getValveTest(@AuthenticationPrincipal Mono<UserDetails> principal, @RequestBody GetByPatternRequest request, @PathVariable("pageNo") String pageNo) {
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
    Mono<ResponseEntity<ApiResponse<GetTestResponse<AirPumpTestDto>>>> getAirPumpTest(@AuthenticationPrincipal Mono<UserDetails> principal, @RequestBody GetByPatternRequest request, @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get air pump test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting air pump test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("AIR_PUMP");
                    return testService.getAirPumpTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/power-pcb-test")
    public Mono<ResponseEntity<CommonResponse>> addPowerPCBTest(@RequestBody PowerPCBTestAddRequest powerPCBTestAddRequest) {
        if (!powerPCBTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(powerPCBTestAddRequest.getHashKey());
        }
        log.info("Request received to add power pcb test: {}", powerPCBTestAddRequest);
        return testService.addPowerPCBTest(powerPCBTestAddRequest);
    }

    @PostMapping("/get/power-pcb-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<PowerPCBTestDto>>>> getPowerPCBTest(@AuthenticationPrincipal Mono<UserDetails> principal, @RequestBody GetByPatternRequest request, @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get power pcb test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting power pcb test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("POWER_PCB");
                    return testService.getPowerPCBTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/air-pump-v2-test")
    public Mono<ResponseEntity<CommonResponse>> addAirPumpV2Test(@RequestBody AirPumpV2TestAddRequest airPumpV2TestAddRequest) {
        if (!airPumpV2TestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(airPumpV2TestAddRequest.getHashKey());
        }
        log.info("Request received to add air pump v2 : {}", airPumpV2TestAddRequest);
        return testService.addAirPumpV2Test(airPumpV2TestAddRequest);
    }

    @PostMapping("/get/air-pump-v2-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<AirPumpV2TestDto>>>> getAirPumpV2Test(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                          @RequestBody GetByPatternRequest request,
                                                                                          @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get air pump v2 test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting air pump v2 test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("AIR_PUMP_V2");
                    return testService.getAirPumpV2Test(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/power-pcb-v2-test")
    public Mono<ResponseEntity<CommonResponse>> addPowerPCBV2Test(@RequestBody PowerPCBV2TestAddRequest powerPCBV2TestAddRequest) {
        if (!powerPCBV2TestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(powerPCBV2TestAddRequest.getHashKey());
        }
        log.info("Request received to add power pcb v2 : {}", powerPCBV2TestAddRequest);
        return testService.addPowerPCBV2Test(powerPCBV2TestAddRequest);
    }

    @PostMapping("/get/power-pcb-v2-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<PowerPCBV2TestDto>>>> getPowerPCBV2Test(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                            @RequestBody GetByPatternRequest request,
                                                                                            @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get power pcb v2 test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting power pcb v2 test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("POWER_PCB_V2");
                    return testService.getPowerPCBV2Test(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/power-supply-v2-test")
    public Mono<ResponseEntity<CommonResponse>> addPowerSupplyV2Test(@RequestBody PowerSupplyV2TestAddRequest powerSupplyV2TestAddRequest) {
        if (!powerSupplyV2TestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(powerSupplyV2TestAddRequest.getHashKey());
        }
        log.info("Request received to add power supply v2 : {}", powerSupplyV2TestAddRequest);
        return testService.addPowerSupplyV2Test(powerSupplyV2TestAddRequest);
    }

    @PostMapping("/get/power-supply-v2-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<PowerSupplyV2TestDto>>>> getPowerSupplyV2Test(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                  @RequestBody GetByPatternRequest request,
                                                                                                  @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get power supply v2 test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting power supply v2 test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("POWER_SUPPLY_V2");
                    return testService.getPowerSupplyV2Test(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/op-valve-test")
    public Mono<ResponseEntity<CommonResponse>> addOpValveTest(@RequestBody OpValveTestAddRequest opValveTestAddRequest) {
        if (!opValveTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(opValveTestAddRequest.getHashKey());
        }
        log.info("Request received to add op valve : {}", opValveTestAddRequest);
        return testService.addOpValveTest(opValveTestAddRequest);
    }

    @PostMapping("/get/op-valve-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<OpValveTestDto>>>> getOpValveTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                      @RequestBody GetByPatternRequest request,
                                                                                      @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get op valve test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting op valve test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("OP_VALVE");
                    return testService.getOpValveTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/valve-sequence-test")
    public Mono<ResponseEntity<CommonResponse>> addValveSequenceTest(@RequestBody ValveSequenceTestAddRequest valveSequenceTestAddRequest) {
        if (!valveSequenceTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(valveSequenceTestAddRequest.getHashKey());
        }
        log.info("Request received to add valve sequence : {}", valveSequenceTestAddRequest);
        return testService.addValveSequenceTest(valveSequenceTestAddRequest);
    }

    @PostMapping("/get/valve-sequence-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<ValveSequenceTestDto>>>> getValveSequenceTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                  @RequestBody GetByPatternRequest request,
                                                                                                  @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get valve sequence test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting valve sequence test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("VALVE_SEQUENCE");
                    return testService.getValveSequenceTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/valve-card-test")
    public Mono<ResponseEntity<CommonResponse>> addValveCardTest(@RequestBody ValveCardTestAddRequest valveCardTestAddRequest) {
        if (!valveCardTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(valveCardTestAddRequest.getHashKey());
        }
        log.info("Request received to add valve card : {}", valveCardTestAddRequest);
        return testService.addValveCardTest(valveCardTestAddRequest);
    }

    @PostMapping("/get/valve-card-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<ValveCardTestDto>>>> getValveCardTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                          @RequestBody GetByPatternRequest request,
                                                                                          @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get valve card test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting valve card test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("VALVE_CARD");
                    return testService.getValveCardTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/mani-fold-leak-test")
    public Mono<ResponseEntity<CommonResponse>> addManiFoldLeakTest(@RequestBody ManiFoldLeakTestAddRequest maniFoldLeakTestAddRequest) {
        if (!maniFoldLeakTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(maniFoldLeakTestAddRequest.getHashKey());
        }
        log.info("Request received to add manifold leak : {}", maniFoldLeakTestAddRequest);
        return testService.addManiFoldLeakTest(maniFoldLeakTestAddRequest);
    }

    @PostMapping("/get/mani-fold-leak-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<ManiFoldLeakTestDto>>>> getManiFoldLeakTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                                @RequestBody GetByPatternRequest request,
                                                                                                @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get manifold leak test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting manifold leak test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("MANI_FOLD_LEAK");
                    return testService.getManiFoldLeakTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/ui-pcb-test")
    public Mono<ResponseEntity<CommonResponse>> addUiPcbTest(@RequestBody UiPcbTestAddRequest uiPcbTestAddRequest) {
        if (!uiPcbTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(uiPcbTestAddRequest.getHashKey());
        }
        log.info("Request received to add ui pcb : {}", uiPcbTestAddRequest);
        return testService.addUiPcbTest(uiPcbTestAddRequest);
    }

    @PostMapping("/get/ui-pcb-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<UiPcbTestDto>>>> getUiPcbTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                  @RequestBody GetByPatternRequest request,
                                                                                  @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get ui pcb test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting ui pcb test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("UI_PCB");
                    return testService.getUiPcbTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/cable-test")
    public Mono<ResponseEntity<CommonResponse>> addCableTest(@RequestBody CableTestAddRequest cableTestAddRequest) {
        if (!cableTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(cableTestAddRequest.getHashKey());
        }
        log.info("Request received to add cable : {}", cableTestAddRequest);
        return testService.addCableTest(cableTestAddRequest);
    }

    @PostMapping("/get/cable-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<CableTestDto>>>> getCableTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                                  @RequestBody GetByPatternRequest request,
                                                                                  @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get cable test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting cable test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("CABLE");
                    return testService.getCableTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/fan-test")
    public Mono<ResponseEntity<CommonResponse>> addFanTest(@RequestBody FanTestAddRequest fanTestAddRequest) {
        if (!fanTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(fanTestAddRequest.getHashKey());
        }
        log.info("Request received to add fan : {}", fanTestAddRequest);
        return testService.addFanTest(fanTestAddRequest);
    }

    @PostMapping("/get/fan-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<FanTestDto>>>> getFanTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                              @RequestBody GetByPatternRequest request,
                                                                              @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get fan test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting fan test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("FAN");
                    return testService.getFanTest(request, userDetails, pageNo);
                });
    }

    @PostMapping("/add/display-test")
    public Mono<ResponseEntity<CommonResponse>> addDisplayTest(@RequestBody DisplayTestAddRequest displayTestAddRequest) {
        if (!displayTestAddRequest.getHashKey().equals(hashKey)) {
            return sendInvalidResponse(displayTestAddRequest.getHashKey());
        }
        log.info("Request received to add display : {}", displayTestAddRequest);
        return testService.addDisplayTest(displayTestAddRequest);
    }

    @PostMapping("/get/display-test/{pageNo}")
    Mono<ResponseEntity<ApiResponse<GetTestResponse<DisplayTestDto>>>> getDisplayTest(@AuthenticationPrincipal Mono<UserDetails> principal,
                                                                              @RequestBody GetByPatternRequest request,
                                                                              @PathVariable("pageNo") String pageNo) {
        log.info("Received request to get display test with pattern: {}", request.getFilterValue());
        return principal
                .flatMap(userDetails -> {
                    log.info("Getting display test with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    request.setRequestType("DISPLAY");
                    return testService.getDisplayTest(request, userDetails, pageNo);
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
