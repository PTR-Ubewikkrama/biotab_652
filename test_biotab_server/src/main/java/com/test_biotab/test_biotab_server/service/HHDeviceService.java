package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.DeviceHHDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface HHDeviceService {
    Mono<ResponseEntity<ApiResponse<Void>>> add(HHDeviceAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<GetHHDevicesResponse>>> getDevices(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<ApiResponse<DeviceHHDto>>> getDeviceByCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateHHDevice(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ComponentVerificationResponse>>> verifyComponent(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateAirPumpTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePowerSupplyTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveTestCode(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> delete(ValidateRequest request, UserDetails userDetails);
}
