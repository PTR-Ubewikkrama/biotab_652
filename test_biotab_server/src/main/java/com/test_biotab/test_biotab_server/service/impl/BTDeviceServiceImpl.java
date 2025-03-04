package com.test_biotab.test_biotab_server.service.impl;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.BTDeviceDto;
import com.test_biotab.test_biotab_server.entity.*;
import com.test_biotab.test_biotab_server.repository.*;
import com.test_biotab.test_biotab_server.service.BTDeviceService;
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
public class BTDeviceServiceImpl implements BTDeviceService {

    private final BTDeviceRepository btDeviceRepository;
    private final AirPumpTestRepository airPumpTestRepository;
    private final AirPumpV2TestRepository airPumpV2TestRepository;
    private final PowerSupplyTestRepository powerSupplyTestRepository;
    private final ValveTestRepository valveTestRepository;
    private final ValveBtDeviceRepository valveBtDeviceRepository;
    private final PowerPCBTestRepository powerPCBTestRepository;
    private final PowerPCBV2TestRepository powerPCBV2TestRepository;
    private final FanTestRepository fanTestRepository;
    private final UiPcbTestRepository uiPcbTestRepository;
    private final ManiFoldLeakTestRepository maniFoldLeakTestRepository;
    private final ValveCardTestRepository valveCardTestRepository;
    private final OpValveTestRepository opValveTestRepository;
    private final ValveSequenceTestRepository valveSequenceTestRepository;
    private final PowerSupplyV2TestRepository powerSupplyV2TestRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Mono<ResponseEntity<ApiResponse<Void>>> add(BTDeviceAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(btDeviceAddRequest -> {

                    log.info("Adding BT device: {} by user: {}", request, userDetails.getUsername());
                    BTDevice device = btDeviceRepository.findByCode(request.getDeviceCode());

                    if (device != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("BT device already exists")
                                .build());
                    }

                    BTDevice btDevice = BTDevice.builder()
                            .deviceCode(btDeviceAddRequest.getDeviceCode())
                            .powerPcbCode(btDeviceAddRequest.getPowerPcbCode())
                            .powerPcbCodeStatus(btDeviceAddRequest.getPowerPcbCodeStatus())
                            .pumpCode(btDeviceAddRequest.getPumpCode())
                            .pumpCodeStatus(btDeviceAddRequest.getPumpCodeStatus())
                            .fanCode(btDeviceAddRequest.getFanCode())
                            .fanCodeStatus(btDeviceAddRequest.getFanCodeStatus())
                            .uiPcbCode(btDeviceAddRequest.getUiPcbCode())
                            .uiPcbCodeStatus(btDeviceAddRequest.getUiPcbCodeStatus())
                            .encoderCode(btDeviceAddRequest.getEncoderCode())
                            .encoderCodeStatus(btDeviceAddRequest.getEncoderCodeStatus())
                            .mainPcbCode(btDeviceAddRequest.getMainPcbCode())
                            .mainPcbCodeStatus(btDeviceAddRequest.getMainPcbCodeStatus())
                            .manifoldCode(btDeviceAddRequest.getManifoldCode())
                            .manifoldCodeStatus(btDeviceAddRequest.getManifoldCodeStatus())
                            .valveCardInsideCableSetCode(btDeviceAddRequest.getValveCardInsideCableSetCode())
                            .valveCardInsideCableSetCodeStatus(btDeviceAddRequest.getValveCardInsideCableSetCodeStatus())
                            .valveCardInputOutputCableSetCode(btDeviceAddRequest.getValveCardInputOutputCableSetCode())
                            .valveCardInputOutputCableSetCodeStatus(btDeviceAddRequest.getValveCardInputOutputCableSetCodeStatus())
                            .overPressureValveCode(btDeviceAddRequest.getOverPressureValveCode())
                            .overPressureValveCodeStatus(btDeviceAddRequest.getOverPressureValveCodeStatus())
                            .powerCableCode(btDeviceAddRequest.getPowerCableCode())
                            .uiCableCode(btDeviceAddRequest.getUiCableCode())
                            .displayCode(btDeviceAddRequest.getDisplayCode())
                            .frontBracketAssemblyCode(btDeviceAddRequest.getFrontBracketAssemblyCode())
                            .frontBracketAssemblyCodeStatus(btDeviceAddRequest.getFrontBracketAssemblyCodeStatus())
                            .powerAdaptorCode(btDeviceAddRequest.getPowerAdaptorCode())
                            .powerAdaptorCodeStatus(btDeviceAddRequest.getPowerAdaptorCodeStatus())
                            .enclosureTopCode(btDeviceAddRequest.getEnclosureTopCode())
                            .enclosureBottomCode(btDeviceAddRequest.getEnclosureBottomCode())
                            .backVentCode(btDeviceAddRequest.getBackVentCode())
                            .fanMountCode(btDeviceAddRequest.getFanMountCode())
                            .encoderSupporterCode(btDeviceAddRequest.getEncoderSupporterCode())
                            .pcbHolderCode(btDeviceAddRequest.getPcbHolderCode())
                            .createdBy(userDetails.getUsername())
                            .dateTime(LocalDateTime.now())
                            .build();

                    BTDevice temp = btDeviceRepository.save(btDevice);

                    if (temp == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Error occurred while adding BT device")
                                .build());
                    }

                    btDeviceAddRequest.getValveCards().forEach(valveCard -> {
                        ValveCardBTDevice valveCardBTDevice = ValveCardBTDevice.builder()
                                .btDeviceCode(temp.getDeviceId())
                                .valveCode(valveCard)
                                .valveCodeStatus("PASS")
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy(userDetails.getUsername())
                                .build();
                        valveBtDeviceRepository.save(valveCardBTDevice);
                    });

                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("BT device added successfully")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding BT device")
                        .build())));

    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetBTDevicesResponse>>> getDevices(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting HH devices by pattern: {} by user: {}", req, userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return btDeviceRepository.findByCustomQuery(getCustomQuery(request, userDetails));
                    } else {
                        return btDeviceRepository.findByCustomQuery(getCustomQuery(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(devices -> Mono.just(btDeviceRepository.countByCustomQuery(getCustomCountQuery(request, userDetails)))
                        .map(total -> ApiResponse.<GetBTDevicesResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetBTDevicesResponse.builder()
                                        .devices(getHHDeviceDtosFromDevices(devices))
                                        .total(total)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Devices")));
    }

    private List<BTDeviceDto> getHHDeviceDtosFromDevices(List<BTDevice> devices) {
        return devices.stream()
                .map(device -> BTDeviceDto.builder()
                        .deviceCode(device.getDeviceCode())
                        .powerPcbCode(device.getPowerPcbCode())
                        .powerPcbCodeStatus(device.getPowerPcbCodeStatus())
                        .pumpCode(device.getPumpCode())
                        .pumpCodeStatus(device.getPumpCodeStatus())
                        .fanCode(device.getFanCode())
                        .fanCodeStatus(device.getFanCodeStatus())
                        .uiPcbCode(device.getUiPcbCode())
                        .uiPcbCodeStatus(device.getUiPcbCodeStatus())
                        .encoderCode(device.getEncoderCode())
                        .encoderCodeStatus(device.getEncoderCodeStatus())
                        .mainPcbCode(device.getMainPcbCode())
                        .mainPcbCodeStatus(device.getMainPcbCodeStatus())
                        .manifoldCode(device.getManifoldCode())
                        .manifoldCodeStatus(device.getManifoldCodeStatus())
                        .valveCardInsideCableSetCode(device.getValveCardInsideCableSetCode())
                        .valveCardInsideCableSetCodeStatus(device.getValveCardInsideCableSetCodeStatus())
                        .valveCardInputOutputCableSetCode(device.getValveCardInputOutputCableSetCode())
                        .valveCardInputOutputCableSetCodeStatus(device.getValveCardInputOutputCableSetCodeStatus())
                        .overPressureValveCode(device.getOverPressureValveCode())
                        .overPressureValveCodeStatus(device.getOverPressureValveCodeStatus())
                        .powerCableCode(device.getPowerCableCode())
                        .uiCableCode(device.getUiCableCode())
                        .displayCode(device.getDisplayCode())
                        .frontBracketAssemblyCode(device.getFrontBracketAssemblyCode())
                        .frontBracketAssemblyCodeStatus(device.getFrontBracketAssemblyCodeStatus())
                        .powerAdaptorCode(device.getPowerAdaptorCode())
                        .powerAdaptorCodeStatus(device.getPowerAdaptorCodeStatus())
                        .enclosureTopCode(device.getEnclosureTopCode())
                        .enclosureBottomCode(device.getEnclosureBottomCode())
                        .backVentCode(device.getBackVentCode())
                        .fanMountCode(device.getFanMountCode())
                        .encoderSupporterCode(device.getEncoderSupporterCode())
                        .pcbHolderCode(device.getPcbHolderCode())
                        .valveCards(valveBtDeviceRepository.findByBtDeviceCode(device.getDeviceId()).stream()
                                .map(ValveCardBTDevice::getValveCode)
                                .toList())
                        .createdBy(device.getCreatedBy())
                        .dateTime(device.getDateTime().toString())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<BTDeviceDto>>> getDeviceByCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Getting BT device by code: {} by user: {}", validateRequest, userDetails.getUsername());
                    BTDevice device = btDeviceRepository.findByCode(validateRequest.getCode());

                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<BTDeviceDto>builder()
                                .status("E1000")
                                .statusDescription("BT device not found")
                                .build());
                    }

                    return ResponseEntity.ok(ApiResponse.<BTDeviceDto>builder()
                            .status("S1000")
                            .statusDescription("BT device found")
                            .data(BTDeviceDto.builder()
                                    .deviceCode(device.getDeviceCode())
                                    .powerPcbCode(device.getPowerPcbCode())
                                    .powerPcbCodeStatus(device.getPowerPcbCodeStatus())
                                    .pumpCode(device.getPumpCode())
                                    .pumpCodeStatus(device.getPumpCodeStatus())
                                    .fanCode(device.getFanCode())
                                    .fanCodeStatus(device.getFanCodeStatus())
                                    .uiPcbCode(device.getUiPcbCode())
                                    .uiPcbCodeStatus(device.getUiPcbCodeStatus())
                                    .encoderCode(device.getEncoderCode())
                                    .encoderCodeStatus(device.getEncoderCodeStatus())
                                    .mainPcbCode(device.getMainPcbCode())
                                    .mainPcbCodeStatus(device.getMainPcbCodeStatus())
                                    .manifoldCode(device.getManifoldCode())
                                    .manifoldCodeStatus(device.getManifoldCodeStatus())
                                    .valveCardInsideCableSetCode(device.getValveCardInsideCableSetCode())
                                    .valveCardInsideCableSetCodeStatus(device.getValveCardInsideCableSetCodeStatus())
                                    .valveCardInputOutputCableSetCode(device.getValveCardInputOutputCableSetCode())
                                    .valveCardInputOutputCableSetCodeStatus(device.getValveCardInputOutputCableSetCodeStatus())
                                    .overPressureValveCode(device.getOverPressureValveCode())
                                    .overPressureValveCodeStatus(device.getOverPressureValveCodeStatus())
                                    .powerCableCode(device.getPowerCableCode())
                                    .uiCableCode(device.getUiCableCode())
                                    .displayCode(device.getDisplayCode())
                                    .frontBracketAssemblyCode(device.getFrontBracketAssemblyCode())
                                    .frontBracketAssemblyCodeStatus(device.getFrontBracketAssemblyCodeStatus())
                                    .powerAdaptorCode(device.getPowerAdaptorCode())
                                    .powerAdaptorCodeStatus(device.getPowerAdaptorCodeStatus())
                                    .enclosureTopCode(device.getEnclosureTopCode())
                                    .enclosureBottomCode(device.getEnclosureBottomCode())
                                    .backVentCode(device.getBackVentCode())
                                    .fanMountCode(device.getFanMountCode())
                                    .encoderSupporterCode(device.getEncoderSupporterCode())
                                    .pcbHolderCode(device.getPcbHolderCode())
                                    .valveCards(valveBtDeviceRepository.findByBtDeviceCode(device.getDeviceId()).stream()
                                            .map(ValveCardBTDevice::getValveCode)
                                            .toList())
                                    .createdBy(device.getCreatedBy())
                                    .dateTime(device.getDateTime().toString())
                                    .build())
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<BTDeviceDto>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while getting device")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateBTDevice(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating BT device: {} by user: {}", validateRequest, userDetails.getUsername());
                    BTDevice device = btDeviceRepository.findByCode(validateRequest.getCode());

                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("BT device not found")
                                .build());
                    }

                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E1200")
                            .statusDescription("BT device found")
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
                    BTDevice device = btDeviceRepository.findByCode(validateRequest.getCode());

                    if (device != null) {
                        return ResponseEntity.ok(ApiResponse.<ComponentVerificationResponse>builder()
                                .status("S1000")
                                .statusDescription("Component verified successfully")
                                .data(ComponentVerificationResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .componentCode(request.getCode())
                                        .componentType("BT_DEVICE")
                                        .componentStatus("VERIFIED")
                                        .build())
                                .build());
                    } else {
                        List<String> componentTypes = List.of("PCB", "VALVE", "AIR_PUMP", "LATCH_BUTTON",
                                "OVER_PRESSURE_VALVE", "BATTERY", "ENCLOSURE", "AIR_BLADDER", "POWER_SUPPLY");

                        TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE " +
                                "d.pcbTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.valveTestOneCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.valveTestTwoCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.airPumpTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.latchButtonTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.overPressureValveTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.batteryTestCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.enclosureCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.airBladderCode LIKE '%" + validateRequest.getCode() + "%' OR " +
                                "d.powerSupplyTestCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);

                        List<BTDevice> devices = query.getResultList();

                        if (!devices.isEmpty()) {
                            BTDevice deviceToVerify = devices.getFirst();
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

    private String getComponentType(BTDevice deviceToVerify, ValidateRequest request) {
        if (deviceToVerify.getDeviceCode().contains(request.getCode())) {
            return "BT_DEVICE";
//        }
//        else if (deviceToVerify.getPcbTestCode().contains(request.getCode())) {
//            return "PCB";
//        } else if (deviceToVerify.getValveTestOneCode().contains(request.getCode()) ||
//                deviceToVerify.getValveTestTwoCode().contains(request.getCode())) {
//            return "VALVE";
//        } else if (deviceToVerify.getAirPumpTestCode().contains(request.getCode())) {
//            return "AIR_PUMP";
//        } else if (deviceToVerify.getLatchButtonTestCode().contains(request.getCode())) {
//            return "LATCH_BUTTON";
//        } else if (deviceToVerify.getOverPressureValveTestCode().contains(request.getCode())) {
//            return "OVER_PRESSURE_VALVE";
//        } else if (deviceToVerify.getBatteryTestCode().contains(request.getCode())) {
//            return "BATTERY";
//        } else if (deviceToVerify.getEnclosureCode().contains(request.getCode())) {
//            return "ENCLOSURE";
//        } else if (deviceToVerify.getAirBladderCode().contains(request.getCode())) {
//            return "AIR_BLADDER";
//        } else if (deviceToVerify.getPowerSupplyTestCode().contains(request.getCode())) {
//            return "POWER_SUPPLY";
        } else {
            return "UNKNOWN";
        }
    }

    private TypedQuery<BTDevice> getCustomQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT d FROM BTDevice d ");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<BTDevice> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY d.dateTime DESC").toString(), BTDevice.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<Long> getCustomCountQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(d) FROM BTDevice d ");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private static void calculateFilterParts(GetByPatternRequest request, List<String> filterParts, UserDetails userDetails) {
        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            switch (request.getFilterType()) {
                case "CODE" -> filterParts.add("deviceCode LIKE '%" + request.getFilterValue() + "%'");
                case "POWER_PCB" -> filterParts.add("powerPcbCode LIKE '%" + request.getFilterValue() + "%'");
                case "PUMP" -> filterParts.add("pumpCode LIKE '%" + request.getFilterValue() + "%'");
                case "FAN" -> filterParts.add("fanCode LIKE '%" + request.getFilterValue() + "%'");
                case "UI_PCB" -> filterParts.add("uiPcbCode LIKE '%" + request.getFilterValue() + "%'");
                case "ENCODER" -> filterParts.add("encoderCode LIKE '%" + request.getFilterValue() + "%'");
                case "MAIN_PCB" -> filterParts.add("mainPcbCode LIKE '%" + request.getFilterValue() + "%'");
                case "MANIFOLD" -> filterParts.add("manifoldCode LIKE '%" + request.getFilterValue() + "%'");
                case "VALVE_CARD_INSIDE_CABLE_SET" ->
                        filterParts.add("valveCardInsideCableSetCode LIKE '%" + request.getFilterValue() + "%'");
                case "VALVE_CARD_INPUT_OUTPUT_CABLE_SET" ->
                        filterParts.add("valveCardInputOutputCableSetCode LIKE '%" + request.getFilterValue() + "%'");
                case "OVER_PRESSURE_VALVE" ->
                        filterParts.add("overPressureValveCode LIKE '%" + request.getFilterValue() + "%'");
                case "POWER_CABLE" -> filterParts.add("powerCableCode LIKE '%" + request.getFilterValue() + "%'");
                case "UI_CABLE" -> filterParts.add("uiCableCode LIKE '%" + request.getFilterValue() + "%'");
                case "DISPLAY" -> filterParts.add("displayCode LIKE '%" + request.getFilterValue() + "%'");
                case "FRONT_BRACKET_ASSEMBLY" ->
                        filterParts.add("frontBracketAssemblyCode LIKE '%" + request.getFilterValue() + "%'");
                case "POWER_ADAPTOR" -> filterParts.add("powerAdaptorCode LIKE '%" + request.getFilterValue() + "%'");
                case "ENCLOSURE_TOP" -> filterParts.add("enclosureTopCode LIKE '%" + request.getFilterValue() + "%'");
                case "ENCLOSURE_BOTTOM" ->
                        filterParts.add("enclosureBottomCode LIKE '%" + request.getFilterValue() + "%'");
                case "BACK_VENT" -> filterParts.add("backVentCode LIKE '%" + request.getFilterValue() + "%'");
                case "FAN_MOUNT" -> filterParts.add("fanMountCode LIKE '%" + request.getFilterValue() + "%'");
                case "ENCODER_SUPPORTER" ->
                        filterParts.add("encoderSupporterCode LIKE '%" + request.getFilterValue() + "%'");
                case "PCB_HOLDER" -> filterParts.add("pcbHolderCode LIKE '%" + request.getFilterValue() + "%'");
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
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.pumpCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Air Pump test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        AirPumpTestData airPumpTest = airPumpTestRepository.findByCode(validateRequest.getCode());
                        AirPumpV2TestData airPumpV2Test = airPumpV2TestRepository.findByCode(validateRequest.getCode());
                        if (airPumpTest == null && airPumpV2Test == null) {
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
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.powerAdaptorCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Power Supply test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        PowerSupplyTestData powerSupplyTest = powerSupplyTestRepository.findByCode(validateRequest.getCode());
                        PowerSupplyV2TestData powerSupplyV2Test = powerSupplyV2TestRepository.findByCode(validateRequest.getCode());
                        if (powerSupplyTest == null && powerSupplyV2Test == null) {
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
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveCardTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Valve test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<ValveCardBTDevice> query = entityManager.createQuery("SELECT v FROM ValveCardBTDevice v WHERE v.valveCode LIKE '%" + validateRequest.getCode() + "%'", ValveCardBTDevice.class);
                    List<ValveCardBTDevice> resultList = query.getResultList();

                    if (!resultList.isEmpty()) {
                        ValveCardBTDevice cardBTDevice = resultList.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Valve test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(cardBTDevice.getValveCode())
                                        .build())
                                .build());
                    } else {
                        ValveCardTestData valveTest = valveCardTestRepository.findByCode(validateRequest.getCode());
                        if (valveTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Valve card test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Valve card test code found")
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
                    log.info("Deleting BT device: {} by user: {}", validateRequest, userDetails.getUsername());
                    BTDevice device = btDeviceRepository.findByCode(validateRequest.getCode());

                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("BT device not found")
                                .build());
                    }

                    valveBtDeviceRepository.deleteByBtDeviceCode(device.getDeviceId());

                    btDeviceRepository.deleteByCode(device.getDeviceCode());
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("BT device deleted successfully")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while deleting device")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validatePcbTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating PCB test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.powerPcbCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("PCB test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        PowerPCBTestData powerPCBTest = powerPCBTestRepository.findByCode(validateRequest.getCode());
                        PowerPCBV2TestData powerPCBV2Test = powerPCBV2TestRepository.findByCode(validateRequest.getCode());
                        if (powerPCBTest == null && powerPCBV2Test == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("PCB test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("PCB test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating PCB test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateFanTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Fan test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.fanCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Fan test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        FanTestData fanTest = fanTestRepository.findByCode(validateRequest.getCode());
                        if (fanTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Fan test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Fan test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Fan test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateUiPcbTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating UI PCB test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.uiPcbCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("UI PCB test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        UiPcbTestData uiPcbTest = uiPcbTestRepository.findByCode(validateRequest.getCode());
                        if (uiPcbTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("UI PCB test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("UI PCB test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating UI PCB test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateManifoldTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Manifold test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.manifoldCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Manifold test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        ManiFoldLeakTestData manifoldTest = maniFoldLeakTestRepository.findByCode(validateRequest.getCode());
                        if (manifoldTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Manifold test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Manifold test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Manifold test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateOverPressureValveTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Over Pressure Valve test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.overPressureValveCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Over Pressure Valve test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        OpValveTestData overPressureValveTest = opValveTestRepository.findByCode(validateRequest.getCode());
                        if (overPressureValveTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Over Pressure Valve test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Over Pressure Valve test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Over Pressure Valve test code")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateComponentResponse>>> validateValveSequenceTestCode(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Valve Sequence test code: {} by user: {}", validateRequest, userDetails.getUsername());
                    TypedQuery<BTDevice> query = entityManager.createQuery("SELECT d FROM BTDevice d WHERE d.frontBracketAssemblyCode LIKE '%" + validateRequest.getCode() + "%'", BTDevice.class);
                    List<BTDevice> devices = query.getResultList();

                    if (!devices.isEmpty()) {
                        BTDevice device = devices.getFirst();
                        return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                .status("E1000")
                                .statusDescription("Valve Sequence test code found")
                                .data(ValidateComponentResponse.builder()
                                        .hhDeviceCode(device.getDeviceCode())
                                        .build())
                                .build());
                    } else {
                        ValveSequenceTestData valveSequenceTest = valveSequenceTestRepository.findByCode(validateRequest.getCode());
                        if (valveSequenceTest == null) {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("E1000")
                                    .statusDescription("Valve Sequence test code not found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        } else {
                            return ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Valve Sequence test code found")
                                    .data(ValidateComponentResponse.builder()
                                            .hhDeviceCode(null)
                                            .build())
                                    .build());
                        }
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateComponentResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Valve Sequence test code")
                        .build())));
    }
}
