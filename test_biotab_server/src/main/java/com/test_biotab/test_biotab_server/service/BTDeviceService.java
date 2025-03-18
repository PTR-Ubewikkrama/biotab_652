package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.BTDeviceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface BTDeviceService {
    Mono<ResponseEntity<ApiResponse<Void>>> add(BTDeviceAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<GetBTDevicesResponse>>> getDevices(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<ApiResponse<BTDeviceDto>>> getDeviceByCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateBTDevice(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ComponentVerificationResponse>>> verifyComponent(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateAirPumpTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePowerSupplyTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveCardTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> delete(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePcbTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateFanTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateUiPcbTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateManifoldTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateOverPressureValveTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveSequenceTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateDisplayTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateMainPcbTestCode(ValidateRequest request, UserDetails userDetails);
}
