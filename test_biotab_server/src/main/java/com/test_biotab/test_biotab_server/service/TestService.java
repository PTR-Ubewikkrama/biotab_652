package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface TestService {
    Mono<ResponseEntity<CommonResponse>> addPowerSupplyTest(PowerSupplyTestAddRequest powerSupplyTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerSupplyTestDto>>>> getPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addValveTest(ValveTestAddRequest valveTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveTestDto>>>> getValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addAirPumpTest(AirPumpTestAddRequest airPumpTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<AirPumpTestDto>>>> getAirPumpTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addPowerPCBTest(PowerPCBTestAddRequest powerPCBTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerPCBTestDto>>>> getPowerPCBTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addAirPumpV2Test(AirPumpV2TestAddRequest airPumpV2TestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<AirPumpV2TestDto>>>> getAirPumpV2Test(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addPowerPCBV2Test(PowerPCBV2TestAddRequest powerPCBV2TestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerPCBV2TestDto>>>> getPowerPCBV2Test(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addPowerSupplyV2Test(PowerSupplyV2TestAddRequest powerSupplyV2TestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerSupplyV2TestDto>>>> getPowerSupplyV2Test(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addOpValveTest(OpValveTestAddRequest opValveTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<OpValveTestDto>>>> getOpValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addValveSequenceTest(ValveSequenceTestAddRequest valveSequenceTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveSequenceTestDto>>>> getValveSequenceTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addValveCardTest(ValveCardTestAddRequest valveCardTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveCardTestDto>>>> getValveCardTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addManiFoldLeakTest(ManiFoldLeakTestAddRequest maniFoldLeakTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ManiFoldLeakTestDto>>>> getManiFoldLeakTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addUiPcbTest(UiPcbTestAddRequest uiPcbTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<UiPcbTestDto>>>> getUiPcbTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addCableTest(CableTestAddRequest cableTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<CableTestDto>>>> getCableTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addFanTest(FanTestAddRequest fanTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<FanTestDto>>>> getFanTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addDisplayTest(DisplayTestAddRequest displayTestAddRequest);

    Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<DisplayTestDto>>>> getDisplayTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addMainPCBTest(MainPCBTestAddRequest mainPCBTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetTestResponse<MainPCBTestDto>>>> getMainPCBTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);
}
