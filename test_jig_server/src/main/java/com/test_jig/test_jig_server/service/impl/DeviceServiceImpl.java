package com.test_jig.test_jig_server.service.impl;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.domain.DeviceAddRequest;
import com.test_jig.test_jig_server.domain.GetByPatternRequest;
import com.test_jig.test_jig_server.domain.GetDevicesResponse;
import com.test_jig.test_jig_server.entity.Device;
import com.test_jig.test_jig_server.dto.DeviceDto;
import com.test_jig.test_jig_server.repository.DeviceRepository;
import com.test_jig.test_jig_server.service.DeviceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<Device> getDeviceByMac(String mac) {
        return Mono.just(mac)
                .map(deviceRepository::findByMac)
                .onErrorResume(e -> Mono.empty());
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> addDevice(DeviceAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .doOnNext(deviceAddRequest -> {
                    log.info("Adding device: {} by user: {}", deviceAddRequest, userDetails.getUsername());
                    Device device = Device.builder()
                            .deviceType(deviceAddRequest.getDeviceType())
                            .deviceName(deviceAddRequest.getDeviceName())
                            .deviceMac(deviceAddRequest.getDeviceMac())
                            .dateTime(LocalDateTime.now())
                            .build();
                    deviceRepository.save(device);
                })
                .map(deviceAddRequest -> ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("S1000")
                        .statusDescription("Device added successfully")
                        .build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding device")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetDevicesResponse>>> getDevices(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting devices by pattern: {} by user: {}", req, userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return deviceRepository.findByCustomQuery(getCustomQuery(request, userDetails));
                    } else {
                        return deviceRepository.findByCustomQuery(getCustomQuery(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(devices -> Mono.just(deviceRepository.countByCustomQuery(getCustomCountQuery(request, userDetails)))
                        .map(total -> ApiResponse.<GetDevicesResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetDevicesResponse.builder()
                                        .devices(getDeviceDtosFromDevices(devices))
                                        .total(total)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Devices")));
    }

    private List<DeviceDto> getDeviceDtosFromDevices(List<Device> devices) {
        return devices.stream()
                .map(device -> DeviceDto.builder()
                        .id(device.getDeviceId())
                        .deviceType(device.getDeviceType())
                        .deviceName(device.getDeviceName())
                        .deviceMac(device.getDeviceMac())
                        .createdAt(device.getDateTime().toString())
                        .build())
                .toList();
    }

    private TypedQuery<Device> getCustomQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT rm FROM Device rm ");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Device> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY rm.dateTime DESC").toString(), Device.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<Long> getCustomCountQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(rm) FROM Device rm ");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private static void calculateFilterParts(GetByPatternRequest request, List<String> filterParts, UserDetails userDetails) {
        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            switch (request.getFilterType()) {
                case "NAME" -> filterParts.add("deviceName LIKE '%" + request.getFilterValue() + "%'");
                case "MAC" -> filterParts.add("deviceMac LIKE '%" + request.getFilterValue() + "%'");
                case "TYPE" -> filterParts.add("deviceType LIKE '%" + request.getFilterValue() + "%'");
            }
        } else if (request.getFilterValue() != null && !request.getFilterValue().isEmpty()) {
            filterParts.add("deviceName LIKE '%" + request.getFilterValue() + "%' OR deviceMac LIKE '%" + request.getFilterValue() + "%' OR deviceType LIKE '%" + request.getFilterValue() + "%'");
        }

        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            filterParts.add("rm.dateTime < :endDate AND rm.dateTime > :startDate");
        }
    }

    private <T> TypedQuery<T> exchangeDateFilterInQuery(TypedQuery<T> query, GetByPatternRequest request) {
        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            LocalDateTime lastDateStart = LocalDateTime.of(LocalDateTime.parse(request.getFromDate(), formatter).toLocalDate(), LocalTime.MIDNIGHT);
            LocalDateTime lastDateEnd = LocalDateTime.of(LocalDateTime.parse(request.getFromDate(), formatter).toLocalDate(), LocalTime.MAX);

            return query.setParameter("startDate", lastDateStart)
                    .setParameter("endDate", lastDateEnd);
        }
        return query;
    }

    private StringBuilder getQueryByFilterPartsAndBaseQuery(List<String> filterParts, StringBuilder query) {
        if (!filterParts.isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < filterParts.size(); i++) {
                query.append(filterParts.get(i));
                if (i < filterParts.size() - 1) {
                    query.append(" AND ");
                }
            }
        }

        return query;
    }
}
