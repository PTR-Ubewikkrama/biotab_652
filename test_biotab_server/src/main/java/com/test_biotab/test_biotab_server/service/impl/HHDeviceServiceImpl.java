package com.test_biotab.test_biotab_server.service.impl;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.DeviceHHDto;
import com.test_biotab.test_biotab_server.entity.*;
import com.test_biotab.test_biotab_server.repository.*;
import com.test_biotab.test_biotab_server.service.HHDeviceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HHDeviceServiceImpl implements HHDeviceService {

    private final HHDeviceRepository hhDeviceRepository;
    private final AirPumpTestRepository airPumpTestRepository;
    private final PowerSupplyTestRepository powerSupplyTestRepository;
    private final ValveTestRepository valveTestRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Mono<ResponseEntity<ApiResponse<Void>>> add(HHDeviceAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(hhDeviceAddRequest -> {
                    log.info("Adding HH device: {} by user: {}", hhDeviceAddRequest, userDetails.getUsername());
                    HHDevice device = hhDeviceRepository.findByCode(hhDeviceAddRequest.getDeviceCode());

                    if (device != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("HH device already exists")
                                .build());
                    }

                    HHDevice hhDevice = HHDevice.builder()
                            .deviceCode(hhDeviceAddRequest.getDeviceCode())
                            .deviceCodeStatus(hhDeviceAddRequest.getDeviceCodeStatus())
                            .pcbTestCode(hhDeviceAddRequest.getPcbTestCode())
                            .pcbTestCodeStatus(hhDeviceAddRequest.getPcbTestCodeStatus())
                            .valveTestOneCode(hhDeviceAddRequest.getValveTestOneCode())
                            .valveTestOneCodeStatus(hhDeviceAddRequest.getValveTestOneCodeStatus())
                            .valveTestTwoCode(hhDeviceAddRequest.getValveTestTwoCode())
                            .valveTestTwoCodeStatus(hhDeviceAddRequest.getValveTestTwoCodeStatus())
                            .airPumpTestCode(hhDeviceAddRequest.getAirPumpTestCode())
                            .airPumpTestCodeStatus(hhDeviceAddRequest.getAirPumpTestCodeStatus())
                            .latchButtonTestCode(hhDeviceAddRequest.getLatchButtonTestCode())
                            .latchButtonTestCodeStatus(hhDeviceAddRequest.getLatchButtonTestCodeStatus())
                            .overPressureValveTestCode(hhDeviceAddRequest.getOverPressureValveTestCode())
                            .overPressureValveTestCodeStatus(hhDeviceAddRequest.getOverPressureValveTestCodeStatus())
                            .batteryTestCode(hhDeviceAddRequest.getBatteryTestCode())
                            .batteryTestCodeStatus(hhDeviceAddRequest.getBatteryTestCodeStatus())
                            .enclosureCode(hhDeviceAddRequest.getEnclosureCode())
                            .enclosureCodeStatus(hhDeviceAddRequest.getEnclosureCodeStatus())
                            .airBladderCode(hhDeviceAddRequest.getAirBladderCode())
                            .airBladderCodeStatus(hhDeviceAddRequest.getAirBladderCodeStatus())
                            .powerSupplyTestCode(hhDeviceAddRequest.getPowerSupplyTestCode())
                            .powerSupplyTestCodeStatus(hhDeviceAddRequest.getPowerSupplyTestCodeStatus())
                            .createdBy(userDetails.getUsername())
                            .dateTime(LocalDateTime.now())
                            .build();

                    hhDeviceRepository.save(hhDevice);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("HH device added successfully")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding device")
                        .build())));

    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetHHDevicesResponse>>> getDevices(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting HH devices by pattern: {} by user: {}", req, userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return hhDeviceRepository.findByCustomQuery(getCustomQuery(request, userDetails));
                    } else {
                        return hhDeviceRepository.findByCustomQuery(getCustomQuery(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(devices -> Mono.just(hhDeviceRepository.countByCustomQuery(getCustomCountQuery(request, userDetails)))
                        .map(total -> ApiResponse.<GetHHDevicesResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetHHDevicesResponse.builder()
                                        .devices(getHHDeviceDtosFromDevices(devices))
                                        .total(total)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Devices")));
    }

    private List<DeviceHHDto> getHHDeviceDtosFromDevices(List<HHDevice> devices) {
        return devices.stream()
                .map(device -> DeviceHHDto.builder()
                        .deviceCode(device.getDeviceCode())
                        .deviceCodeStatus(device.getDeviceCodeStatus())
                        .pcbTestCode(device.getPcbTestCode())
                        .pcbTestCodeStatus(device.getPcbTestCodeStatus())
                        .valveTestOneCode(device.getValveTestOneCode())
                        .valveTestOneCodeStatus(device.getValveTestOneCodeStatus())
                        .valveTestTwoCode(device.getValveTestTwoCode())
                        .valveTestTwoCodeStatus(device.getValveTestTwoCodeStatus())
                        .airPumpTestCode(device.getAirPumpTestCode())
                        .airPumpTestCodeStatus(device.getAirPumpTestCodeStatus())
                        .latchButtonTestCode(device.getLatchButtonTestCode())
                        .latchButtonTestCodeStatus(device.getLatchButtonTestCodeStatus())
                        .overPressureValveTestCode(device.getOverPressureValveTestCode())
                        .overPressureValveTestCodeStatus(device.getOverPressureValveTestCodeStatus())
                        .batteryTestCode(device.getBatteryTestCode())
                        .batteryTestCodeStatus(device.getBatteryTestCodeStatus())
                        .enclosureCode(device.getEnclosureCode())
                        .enclosureCodeStatus(device.getEnclosureCodeStatus())
                        .airBladderCode(device.getAirBladderCode())
                        .airBladderCodeStatus(device.getAirBladderCodeStatus())
                        .powerSupplyTestCode(device.getPowerSupplyTestCode())
                        .powerSupplyTestCodeStatus(device.getPowerSupplyTestCodeStatus())
                        .createdBy(device.getCreatedBy())
                        .dateTime(device.getDateTime().toString())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<DeviceHHDto>>> getDeviceByCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Getting HH device by code: {} by user: {}", validateRequest, userDetails.getUsername());
                    HHDevice device = hhDeviceRepository.findByCode(validateRequest.getCode());

                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<DeviceHHDto>builder()
                                .status("E1000")
                                .statusDescription("HH device not found")
                                .build());
                    }

                    return ResponseEntity.ok(ApiResponse.<DeviceHHDto>builder()
                            .status("S1000")
                            .statusDescription("HH device found")
                            .data(DeviceHHDto.builder()
                                    .deviceCode(device.getDeviceCode())
                                    .deviceCodeStatus(device.getDeviceCodeStatus())
                                    .pcbTestCode(device.getPcbTestCode())
                                    .pcbTestCodeStatus(device.getPcbTestCodeStatus())
                                    .valveTestOneCode(device.getValveTestOneCode())
                                    .valveTestOneCodeStatus(device.getValveTestOneCodeStatus())
                                    .valveTestTwoCode(device.getValveTestTwoCode())
                                    .valveTestTwoCodeStatus(device.getValveTestTwoCodeStatus())
                                    .airPumpTestCode(device.getAirPumpTestCode())
                                    .airPumpTestCodeStatus(device.getAirPumpTestCodeStatus())
                                    .latchButtonTestCode(device.getLatchButtonTestCode())
                                    .latchButtonTestCodeStatus(device.getLatchButtonTestCodeStatus())
                                    .overPressureValveTestCode(device.getOverPressureValveTestCode())
                                    .overPressureValveTestCodeStatus(device.getOverPressureValveTestCodeStatus())
                                    .batteryTestCode(device.getBatteryTestCode())
                                    .batteryTestCodeStatus(device.getBatteryTestCodeStatus())
                                    .enclosureCode(device.getEnclosureCode())
                                    .enclosureCodeStatus(device.getEnclosureCodeStatus())
                                    .airBladderCode(device.getAirBladderCode())
                                    .airBladderCodeStatus(device.getAirBladderCodeStatus())
                                    .powerSupplyTestCode(device.getPowerSupplyTestCode())
                                    .powerSupplyTestCodeStatus(device.getPowerSupplyTestCodeStatus())
                                    .createdBy(device.getCreatedBy())
                                    .dateTime(device.getDateTime().toString())
                                    .build())
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<DeviceHHDto>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while getting device")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateHHDevice(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating HH device: {} by user: {}", validateRequest, userDetails.getUsername());
                    HHDevice device = hhDeviceRepository.findByCode(validateRequest.getCode());

                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("HH device not found")
                                .build());
                    }

                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E1200")
                            .statusDescription("HH device found")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating device")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ComponentVerificationResponse>>> verifyComponent(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Verifying component: {} by user: {}", validateRequest, userDetails.getUsername());
                    HHDevice device = hhDeviceRepository.findByCode(validateRequest.getCode());

                    if (device != null) {
                        return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                .status("S1000")
                                .statusDescription("Component verified successfully")
                                .data(ComponentVerificationResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .componentCode(request.getCode())
                                        .componentType("HH_DEVICE")
                                        .componentStatus("VERIFIED")
                                        .build())
                                .build());
                    } else {
                        List<String> componentTypes = List.of("PCB", "VALVE", "AIR_PUMP", "LATCH_BUTTON",
                                "OVER_PRESSURE_VALVE", "BATTERY", "ENCLOSURE", "AIR_BLADDER", "POWER_SUPPLY");

                        TypedQuery<HHDevice> query = entityManager.createQuery("SELECT d FROM HHDevice d WHERE " +
                                "d.pcbTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.valveTestOneCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.valveTestTwoCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.airPumpTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.latchButtonTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.overPressureValveTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.batteryTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.enclosureCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.airBladderCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.powerSupplyTestCode LIKE '%" + validateRequest.getCode() + "%'", HHDevice.class);

                        List<HHDevice> devices = query.getResultList();

                        if (!devices.isEmpty()) {
                            HHDevice deviceToVerify = devices.getFirst();
                            return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Component verified successfully")
                                    .data(ComponentVerificationResponse.builder()
                                            .hhDeviceCode(deviceToVerify.getDeviceCode())
                                            .componentCode(request.getCode())
                                            .componentType(getComponentType(deviceToVerify, request))
                                            .componentStatus("VERIFIED")
                                            .build())
                                    .build());
                        } else {
                            for (String componentType : componentTypes) {
                                switch (componentType) {
                                    case "VALVE" -> {
                                        ValveTestData valveTest = valveTestRepository.findByCode(request.getCode());
                                        if (valveTest != null) {
                                            return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                                    .status("S1000")
                                                    .statusDescription("Component verified successfully")
                                                    .data(ComponentVerificationResponse.builder()
                                                            .hhDeviceCode("UNKNOWN")
                                                            .componentCode(request.getCode())
                                                            .componentType("VALVE")
                                                            .componentStatus(valveTest.getStatus() ? "VERIFIED" : "NOT_VERIFIED")
                                                            .build())
                                                    .build());
                                        }
                                    }
                                    case "AIR_PUMP" -> {
                                        AirPumpTestData airPumpTest = airPumpTestRepository.findByCode(request.getCode());
                                        if (airPumpTest != null) {
                                            return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                                    .status("S1000")
                                                    .statusDescription("Component verified successfully")
                                                    .data(ComponentVerificationResponse.builder()
                                                            .hhDeviceCode("UNKNOWN")
                                                            .componentCode(request.getCode())
                                                            .componentType("AIR_PUMP")
                                                            .componentStatus(airPumpTest.getStatus() ? "VERIFIED" : "NOT_VERIFIED")
                                                            .build())
                                                    .build());
                                        }
                                    }
                                    case "POWER_SUPPLY" -> {
                                        PowerSupplyTestData powerSupplyTest = powerSupplyTestRepository.findByCode(request.getCode());
                                        if (powerSupplyTest != null) {
                                            return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                                    .status("S1000")
                                                    .statusDescription("Component verified successfully")
                                                    .data(ComponentVerificationResponse.builder()
                                                            .hhDeviceCode("UNKNOWN")
                                                            .componentCode(request.getCode())
                                                            .componentType("POWER_SUPPLY")
                                                            .componentStatus(powerSupplyTest.getStatus() ? "VERIFIED" : "NOT_VERIFIED")
                                                            .build())
                                                    .build());
                                        }
                                    }
                                    default -> {
                                        return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                                .status("E1000")
                                                .statusDescription("Component not found")
                                                .build());
                                    }
                                }
                            }
                        }
                    }
                    return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                            .status("E1000")
                            .statusDescription("Component not found")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while verifying component")
                        .build())));
    }

    private String getComponentType(HHDevice deviceToVerify, ValidateRequest request) {
        if (deviceToVerify.getDeviceCode().contains(request.getCode())) {
            return "HH_DEVICE";
        } else if (deviceToVerify.getPcbTestCode().contains(request.getCode())) {
            return "PCB";
        } else if (deviceToVerify.getValveTestOneCode().contains(request.getCode()) ||
                deviceToVerify.getValveTestTwoCode().contains(request.getCode())) {
            return "VALVE";
        } else if (deviceToVerify.getAirPumpTestCode().contains(request.getCode())) {
            return "AIR_PUMP";
        } else if (deviceToVerify.getLatchButtonTestCode().contains(request.getCode())) {
            return "LATCH_BUTTON";
        } else if (deviceToVerify.getOverPressureValveTestCode().contains(request.getCode())) {
            return "OVER_PRESSURE_VALVE";
        } else if (deviceToVerify.getBatteryTestCode().contains(request.getCode())) {
            return "BATTERY";
        } else if (deviceToVerify.getEnclosureCode().contains(request.getCode())) {
            return "ENCLOSURE";
        } else if (deviceToVerify.getAirBladderCode().contains(request.getCode())) {
            return "AIR_BLADDER";
        } else if (deviceToVerify.getPowerSupplyTestCode().contains(request.getCode())) {
            return "POWER_SUPPLY";
        } else {
            return "UNKNOWN";
        }
    }

    private TypedQuery<HHDevice> getCustomQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT d FROM HHDevice d ");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<HHDevice> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY d.dateTime DESC").toString(), HHDevice.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<Long> getCustomCountQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(d) FROM HHDevice d ");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private static void calculateFilterParts(GetByPatternRequest request, List<String> filterParts, UserDetails userDetails) {
        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            switch (request.getFilterType()) {
                case "CODE" -> filterParts.add("deviceCode LIKE '%" + request.getFilterValue() + "%'");
                case "PCB" -> filterParts.add("pcbTestCode LIKE '%" + request.getFilterValue() + "%'");
                case "VALVE_ONE" -> filterParts.add("valveTestOneCode LIKE '%" + request.getFilterValue() + "%'");
                case "VALVE_TWO" -> filterParts.add("valveTestTwoCode LIKE '%" + request.getFilterValue() + "%'");
                case "AIR_PUMP" -> filterParts.add("airPumpTestCode LIKE '%" + request.getFilterValue() + "%'");
                case "LATCH_BUTTON" -> filterParts.add("latchButtonTestCode LIKE '%" + request.getFilterValue() + "%'");
                case "OVER_PRESSURE_VALVE" ->
                        filterParts.add("overPressureValveTestCode LIKE '%" + request.getFilterValue() + "%'");
                case "BATTERY" -> filterParts.add("batteryTestCode LIKE '%" + request.getFilterValue() + "%'");
                case "ENCLOSURE" -> filterParts.add("enclosureCode LIKE '%" + request.getFilterValue() + "%'");
                case "AIR_BLADDER" -> filterParts.add("airBladderCode LIKE '%" + request.getFilterValue() + "%'");
                case "POWER_SUPPLY" -> filterParts.add("powerSupplyTestCode LIKE '%" + request.getFilterValue() + "%'");
            }
        }

        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            filterParts.add("d.dateTime < :endDate AND d.dateTime > :startDate");
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

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateAirPumpTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Air Pump test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<HHDevice> query = entityManager.createQuery("SELECT d FROM HHDevice d WHERE d.airPumpTestCode LIKE '%" + validateRequest.getCode() + "%'", HHDevice.class);
                    List<HHDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        HHDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Air Pump test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        AirPumpTestData airPumpTest = airPumpTestRepository.findByCode(validateRequest.getCode());
                        if (airPumpTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Air Pump test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Air Pump test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Air Pump test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePowerSupplyTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Power Supply test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<HHDevice> query = entityManager.createQuery("SELECT d FROM HHDevice d WHERE d.powerSupplyTestCode LIKE '%" + validateRequest.getCode() + "%'", HHDevice.class);
                    List<HHDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        HHDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Power Supply test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        PowerSupplyTestData powerSupplyTest = powerSupplyTestRepository.findByCode(validateRequest.getCode());
                        if (powerSupplyTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Power Supply test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Power Supply test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Power Supply test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Valve test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<HHDevice> query = entityManager.createQuery("SELECT d FROM HHDevice d WHERE d.valveTestOneCode LIKE '%" + validateRequest.getCode() + "%' OR d.valveTestTwoCode LIKE '%" + validateRequest.getCode() + "%'", HHDevice.class);
                    List<HHDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        HHDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Valve test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        ValveTestData valveTest = valveTestRepository.findByCode(validateRequest.getCode());
                        if (valveTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Valve test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Valve test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Valve test code")
                        .build())));
    }

    @Override
    @Transactional
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Deleting HH device: {} by user: {}", validateRequest, userDetails.getUsername());
                    HHDevice device = hhDeviceRepository.findByCode(validateRequest.getCode());

                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("HH device not found")
                                .build());
                    }

                    hhDeviceRepository.deleteByCode(device.getDeviceCode());
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("HH device deleted successfully")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while deleting device")
                        .build())));
    }
}
