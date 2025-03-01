package com.test_biotab.test_biotab_server.service;

import com.test_biotab.test_biotab_server.domain.ApiResponse;
import com.test_biotab.test_biotab_server.domain.DeviceAddRequest;
import com.test_biotab.test_biotab_server.entity.Device;
import com.test_biotab.test_biotab_server.domain.GetByPatternRequest;
import com.test_biotab.test_biotab_server.domain.GetDevicesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface DeviceService {
    Mono<Device> getDeviceByMac(String mac);

    Mono<ResponseEntity<ApiResponse<Void>>> addDevice(DeviceAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<GetDevicesResponse>>> getDevices(GetByPatternRequest request, UserDetails userDetails, String pageNo);
}
