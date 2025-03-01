package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface TestService {
    Mono<ResponseEntity<CommonResponse>> addPowerSupplyTest(PowerSupplyTestAddRequest powerSupplyTestAddRequest);
    Mono<ResponseEntity<ApiResponse<GetPowerSupplyTestResponse>>> getPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);
    Mono<ResponseEntity<CommonResponse>> addValveTest(ValveTestAddRequest valveTestAddRequest);
    Mono<ResponseEntity<ApiResponse<GetValveTestResponse>>> getValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addAirPumpTest(AirPumpTestAddRequest airPumpTestAddRequest);

    Mono<ResponseEntity<CommonResponse>> addBatteryTest(BatteryTestAddRequest batteryTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetAirPumpTestResponse>>> getAirPumpTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<ApiResponse<GetBatteryTestResponse>>> getBatteryTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addOverPressureTest(OverPressureValveTestAddRequest overPressureTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetOverPressureValveTestResponse>>> getOverPressureTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addLatchButtonTest(LatchButtonAddRequest latchButtonAddRequest);

    Mono<ResponseEntity<ApiResponse<GetLatchButtonTestResponse>>> getLatchButtonTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<CommonResponse>> addPcbTest(PcbTestAddRequest pcbTestAddRequest);

    Mono<ResponseEntity<ApiResponse<GetPcbTestResponse>>> getPcbTest(GetByPatternRequest request, UserDetails userDetails, String pageNo);
}
