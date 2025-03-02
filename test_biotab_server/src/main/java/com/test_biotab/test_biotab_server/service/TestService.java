package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface TestService {
    Mono<ResponseEntity<CommonResponse>> addPowerSupplyTest(PowerSupplyTestAddRequest powerSupplyTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetPowerSupplyTestResponse>>> getPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addValveTest(ValveTestAddRequest valveTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetValveTestResponse>>> getValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addAirPumpTest(AirPumpTestAddRequest airPumpTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetAirPumpTestResponse>>> getAirPumpTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addPowerPCBTest(PowerPCBTestAddRequest powerPCBTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetTestResponse<PowerPCBTestDto>>>> getPowerPCBTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

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
}
