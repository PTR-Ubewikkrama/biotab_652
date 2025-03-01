package com.test_jig.test_jig_server.service.impl;

import com.test_jig.test_jig_server.domain.*;
import com.test_jig.test_jig_server.dto.*;
import com.test_jig.test_jig_server.entity.*;
import com.test_jig.test_jig_server.repository.*;
import com.test_jig.test_jig_server.service.DeviceService;
import com.test_jig.test_jig_server.service.TestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {

    private final AirPumpTestRepository airPumpTestRepository;
    private final PowerSupplyTestRepository powerSupplyTestRepository;
    private final BatteryTestRepository batteryTestRepository;
    private final OverPressureValveTestRepository overPressureValveTestRepository;
    private final LatchButtonTestRepository latchButtonTestRepository;
    private final ValveTestRepository valveTestRepository;
    private final PcbTestRepository pcbTestRepository;
    private final DeviceService deviceService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<ResponseEntity<CommonResponse>> addAirPumpTest(AirPumpTestAddRequest airPumpTestAddRequest) {
        return Mono.just(airPumpTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(request.getDeviceMac())
                        .map(device -> toAirPumpTest(airPumpTestAddRequest, device))
                        .map(airPumpTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(airPumpTest -> ResponseEntity.ok(CommonResponse.builder().message("Air pump test added successfully").status("SUCCESS").build()));
    }

    private AirPumpTestData toAirPumpTest(AirPumpTestAddRequest airPumpTestAddRequest, Device device) {
        return AirPumpTestData.builder()
                .device(device)
                .idleVolLowTh(airPumpTestAddRequest.getIdleVolLowTh())
                .idleVolUpTh(airPumpTestAddRequest.getIdleVolUpTh())
                .idleCurUpTh(airPumpTestAddRequest.getIdleCurUpTh())
                .loadVolLowTh(airPumpTestAddRequest.getLoadVolLowTh())
                .loadVolUp(airPumpTestAddRequest.getLoadVolUp())
                .loadCurUpTh(airPumpTestAddRequest.getLoadCurUpTh())
                .setPressure(airPumpTestAddRequest.getSetPressure())
                .serialNumber(airPumpTestAddRequest.getSerialNumber())
                .idleVol(airPumpTestAddRequest.getIdleVol())
                .idleVolStatus(airPumpTestAddRequest.getIdleVolStatus())
                .idleCurrent(airPumpTestAddRequest.getIdleCurrent())
                .idleCurrentStatus(airPumpTestAddRequest.getIdleCurrentStatus())
                .loadVoltage(airPumpTestAddRequest.getLoadVoltage())
                .loadVoltageStatus(airPumpTestAddRequest.getLoadVoltageStatus())
                .loadCurrent(airPumpTestAddRequest.getLoadCurrent())
                .loadCurrentStatus(airPumpTestAddRequest.getLoadCurrentStatus())
                .maxPressure(airPumpTestAddRequest.getFlowRate())
                .maxPressureStatus(airPumpTestAddRequest.getFlowRateStatus())
                .noiseLevel(airPumpTestAddRequest.getNoiseLevel())
                .deviceStatus(airPumpTestAddRequest.getDeviceStatus())
                .status(airPumpTestAddRequest.getIdleVolStatus() &&
                        airPumpTestAddRequest.getIdleCurrentStatus() &&
                        airPumpTestAddRequest.getLoadVoltageStatus() &&
                        airPumpTestAddRequest.getLoadCurrentStatus() &&
                        airPumpTestAddRequest.getFlowRateStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addPowerSupplyTest(PowerSupplyTestAddRequest powerSupplyTestAddRequest) {
        return Mono.just(powerSupplyTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(powerSupplyTestAddRequest.getDeviceMac())
                        .map(device -> toPowerSupplyTest(powerSupplyTestAddRequest, device))
                        .map(powerSupplyTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(powerSupplyTest -> ResponseEntity.ok(CommonResponse.builder().message("Power supply test added successfully").status("SUCCESS").build()));
    }

    private PowerSupplyTestData toPowerSupplyTest(PowerSupplyTestAddRequest powerSupplyTestAddRequest, Device device) {
        return PowerSupplyTestData.builder()
                .device(device)
                .idleVolLowTh(powerSupplyTestAddRequest.getIdleVolLowTh())
                .idleVolUpTh(powerSupplyTestAddRequest.getIdleVolUpTh())
                .loadVolLowTh(powerSupplyTestAddRequest.getLoadVolLowTh())
                .loadVolUpTh(powerSupplyTestAddRequest.getLoadVolUpTh())
                .loadCurUpTh(powerSupplyTestAddRequest.getLoadCurUpTh())
                .serialNumber(powerSupplyTestAddRequest.getSerialNumber())
                .idleVol(powerSupplyTestAddRequest.getIdleVol())
                .idleVolStatus(powerSupplyTestAddRequest.getIdleVolStatus())
                .loadVol(powerSupplyTestAddRequest.getLoadVol())
                .loadVolStatus(powerSupplyTestAddRequest.getLoadVolStatus())
                .loadCurrent(powerSupplyTestAddRequest.getLoadCurrent())
                .loadCurrentStatus(powerSupplyTestAddRequest.getLoadCurrentStatus())
                .operatingPower(powerSupplyTestAddRequest.getOperatingPower())
                .noiseLevel(powerSupplyTestAddRequest.getNoiseLevel())
                .deviceStatus(powerSupplyTestAddRequest.getDeviceStatus())
                .status(powerSupplyTestAddRequest.getIdleVolStatus() &&
                        powerSupplyTestAddRequest.getLoadVolStatus() &&
                        powerSupplyTestAddRequest.getLoadCurrentStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addBatteryTest(BatteryTestAddRequest batteryTestAddRequest) {
        return Mono.just(batteryTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(batteryTestAddRequest.getDeviceMac())
                        .map(device -> toValueTest(batteryTestAddRequest, device))
                        .map(batteryTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(valueTest -> ResponseEntity.ok(CommonResponse.builder().message("Battery test added successfully").status("SUCCESS").build()));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetAirPumpTestResponse>>> getAirPumpTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting air pump test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return airPumpTestRepository.findByCustomQuery(getCustomQueryAirPumpTest(request, userDetails));
                    } else {
                        return airPumpTestRepository.findByCustomQuery(getCustomQueryAirPumpTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(airPumpTestData -> Mono.just(airPumpTestRepository.countByCustomQuery(getCustomCountQueryAirPumpTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetAirPumpTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetAirPumpTestResponse.builder()
                                        .airPumpTests(getAirPumpDtoFromEntity(airPumpTestData))
                                        .totalRecords(total)
//                                        .totalFailed(airPumpTestRepository.countByCustomQuery(
//                                                getCustomQueryForAirPumpFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Air Pump Tests")));

    }

    private TypedQuery<Long> getCustomQueryForAirPumpFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM AirPumpTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetPowerSupplyTestResponse>>> getPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting power supply test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return powerSupplyTestRepository.findByCustomQuery(getCustomQueryPowerSupplyTest(request, userDetails));
                    } else {
                        return powerSupplyTestRepository.findByCustomQuery(getCustomQueryPowerSupplyTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(powerSupplyTestData -> Mono.just(powerSupplyTestRepository.countByCustomQuery(getCustomCountQueryPowerSupplyTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetPowerSupplyTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetPowerSupplyTestResponse.builder()
                                        .powerSupplyTests(getPowerSupplyDtoFromEntity(powerSupplyTestData))
                                        .totalRecords(total)
//                                        .totalFailed(powerSupplyTestRepository.countByCustomQuery(
//                                                getCustomQueryForPowerSupplyFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Power Supply Tests")));
    }

    private TypedQuery<Long> getCustomQueryForPowerSupplyFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM PowerSupplyTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<PowerSupplyTestDto> getPowerSupplyDtoFromEntity(List<PowerSupplyTestData> powerSupplyTestData) {
        return powerSupplyTestData.stream()
                .map(powerSupplyTest -> PowerSupplyTestDto.builder()
                        .testId(powerSupplyTest.getTestId())
                        .deviceId(powerSupplyTest.getDevice().getDeviceId())
                        .idleVolLowTh(powerSupplyTest.getIdleVolLowTh())
                        .idleVolUpTh(powerSupplyTest.getIdleVolUpTh())
                        .loadVolLowTh(powerSupplyTest.getLoadVolLowTh())
                        .loadVolUpTh(powerSupplyTest.getLoadVolUpTh())
                        .loadCurUpTh(powerSupplyTest.getLoadCurUpTh())
                        .serialNumber(powerSupplyTest.getSerialNumber())
                        .idleVol(powerSupplyTest.getIdleVol())
                        .idleVolStatus(powerSupplyTest.getIdleVolStatus())
                        .loadVol(powerSupplyTest.getLoadVol())
                        .loadVolStatus(powerSupplyTest.getLoadVolStatus())
                        .loadCurrent(powerSupplyTest.getLoadCurrent())
                        .loadCurrentStatus(powerSupplyTest.getLoadCurrentStatus())
                        .operatingPower(powerSupplyTest.getOperatingPower())
                        .noiseLevel(powerSupplyTest.getNoiseLevel())
                        .dateTime(powerSupplyTest.getDateTime())
                        .deviceStatus(powerSupplyTest.getDeviceStatus())
                        .status(powerSupplyTest.getStatus())
                        .build())
                .toList();
    }

    private TypedQuery<Long> getCustomCountQueryPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM PowerSupplyTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<PowerSupplyTestData> getCustomQueryPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM PowerSupplyTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<PowerSupplyTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), PowerSupplyTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetBatteryTestResponse>>> getBatteryTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting battery test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return batteryTestRepository.findByCustomQuery(getCustomQueryValueTest(request, userDetails));
                    } else {
                        return batteryTestRepository.findByCustomQuery(getCustomQueryValueTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(valueTestData -> Mono.just(batteryTestRepository.countByCustomQuery(getCustomCountQueryValueTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetBatteryTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetBatteryTestResponse.builder()
                                        .batteryTests(getValueTestDtoFromEntity(valueTestData))
                                        .totalRecords(total)
//                                        .totalFailed(batteryTestRepository.countByCustomQuery(
//                                                getCustomQueryForBatteryFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get battery Tests")));
    }

    private TypedQuery<Long> getCustomQueryForBatteryFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM BatteryTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addOverPressureTest(OverPressureValveTestAddRequest overPressureTestAddRequest) {
        return Mono.just(overPressureTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(overPressureTestAddRequest.getDeviceMac())
                        .map(device -> toOverPressureTest(overPressureTestAddRequest, device))
                        .map(overPressureValveTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(overPressureValveTest -> ResponseEntity.ok(CommonResponse.builder().message("Over pressure test added successfully").status("SUCCESS").build()));
    }

    private OverPressureValveTestData toOverPressureTest(OverPressureValveTestAddRequest overPressureTestAddRequest, Device device) {
        return OverPressureValveTestData.builder()
                .device(device)
                .qrCode(overPressureTestAddRequest.getQrCode())
                .maxPressure(overPressureTestAddRequest.getMaxPressure())
                .maxPressureTime(overPressureTestAddRequest.getMaxPressureTime())
                .maxPressureFlowRate(overPressureTestAddRequest.getMaxPressureFlowRate())
                .normalPressure(overPressureTestAddRequest.getNormalPressure())
                .normalPressureTime(overPressureTestAddRequest.getNormalPressureTime())
                .normalPressureFlowRate(overPressureTestAddRequest.getNormalPressureFlowRate())
                .overPressureValveStatus(overPressureTestAddRequest.getOverPressureValveStatus())
                .status(overPressureTestAddRequest.getOverPressureValveStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetOverPressureValveTestResponse>>> getOverPressureTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting over pressure test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return overPressureValveTestRepository.findByCustomQuery(getCustomQueryOverPressureTest(request, userDetails));
                    } else {
                        return overPressureValveTestRepository.findByCustomQuery(getCustomQueryOverPressureTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(overPressureValveTestData -> Mono.just(overPressureValveTestRepository.countByCustomQuery(getCustomCountQueryOverPressureTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetOverPressureValveTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetOverPressureValveTestResponse.builder()
                                        .overPressureValveTests(getOverPressureValveDtoFromEntity(overPressureValveTestData))
                                        .totalRecords(total)
//                                        .totalFailed(overPressureValveTestRepository.countByCustomQuery(
//                                                getCustomQueryForOverPressureFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Over Pressure Valve Tests")));
    }

    private TypedQuery<Long> getCustomQueryForOverPressureFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM OverPressureValveTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addLatchButtonTest(LatchButtonAddRequest latchButtonAddRequest) {
        return Mono.just(latchButtonAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(latchButtonAddRequest.getDeviceMac())
                        .map(device -> toLatchButtonTest(latchButtonAddRequest, device))
                        .map(latchButtonTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(latchButtonTest -> ResponseEntity.ok(CommonResponse.builder().message("Latch button test added successfully").status("SUCCESS").build()));
    }

    private LatchButtonTestData toLatchButtonTest(LatchButtonAddRequest latchButtonAddRequest, Device device) {
        return LatchButtonTestData.builder()
                .device(device)
                .qrCode(latchButtonAddRequest.getQrCode())
                .buttonOnTestStatus(latchButtonAddRequest.getButtonOnTestStatus())
                .buttonOffTestStatus(latchButtonAddRequest.getButtonOffTestStatus())
                .ledOnTestStatus(latchButtonAddRequest.getLedOnTestStatus())
                .latchButtonStatus(latchButtonAddRequest.getLatchButtonStatus())
                .status(latchButtonAddRequest.getLatchButtonStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetLatchButtonTestResponse>>> getLatchButtonTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting latch button test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return latchButtonTestRepository.findByCustomQuery(getCustomQueryLatchButtonTest(request, userDetails));
                    } else {
                        return latchButtonTestRepository.findByCustomQuery(getCustomQueryLatchButtonTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(latchButtonTestData -> Mono.just(latchButtonTestRepository.countByCustomQuery(getCustomCountQueryLatchButtonTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetLatchButtonTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetLatchButtonTestResponse.builder()
                                        .latchButtonTests(getLatchButtonDtoFromEntity(latchButtonTestData))
                                        .totalRecords(total)
//                                        .totalFailed(latchButtonTestRepository.countByCustomQuery(
//                                                getCustomQueryForLatchButtonFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Latch Button Tests")));
    }

    private TypedQuery<Long> getCustomQueryForLatchButtonFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM LatchButtonTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addValveTest(ValveTestAddRequest valveTestAddRequest) {
        return Mono.just(valveTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(valveTestAddRequest.getDeviceMac())
                        .map(device -> toValveTest(valveTestAddRequest, device))
                        .map(valveTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(valveTest -> ResponseEntity.ok(CommonResponse.builder().message("Valve test added successfully").status("SUCCESS").build()));
    }

    private ValveTestData toValveTest(ValveTestAddRequest valveTestAddRequest, Device device) {
        return ValveTestData.builder()
                .device(device)
                .qrCode(valveTestAddRequest.getQrCode())
                .airChamberLoadingPressure(valveTestAddRequest.getAirChamberLoadingPressure())
                .airChamberStatus(valveTestAddRequest.isAirChamberStatus())
                .v1OutletPressureAfter10MsOnTime(valveTestAddRequest.getV1OutletPressureAfter10MsOnTime())
                .v1OutletOnStatus(valveTestAddRequest.isV1OutletOnStatus())
                .v1OutletPressureAfter10MsOffTime(valveTestAddRequest.getV1OutletPressureAfter10MsOffTime())
                .v1OutletOffStatus(valveTestAddRequest.isV1OutletOffStatus())
                .v2OutletPressureAfter10MsOnTime(valveTestAddRequest.getV2OutletPressureAfter10MsOnTime())
                .v2OutletOnStatus(valveTestAddRequest.isV2OutletOnStatus())
                .v2OutletPressureAfter10MsOffTime(valveTestAddRequest.getV2OutletPressureAfter10MsOffTime())
                .v2OutletOffStatus(valveTestAddRequest.isV2OutletOffStatus())
                .v3OutletPressureAfter10MsOnTime(valveTestAddRequest.getV3OutletPressureAfter10MsOnTime())
                .v3OutletOnStatus(valveTestAddRequest.isV3OutletOnStatus())
                .v3OutletPressureAfter10MsOffTime(valveTestAddRequest.getV3OutletPressureAfter10MsOffTime())
                .v3OutletOffStatus(valveTestAddRequest.isV3OutletOffStatus())
                .valveStatus(valveTestAddRequest.isValveStatus())
                .status(valveTestAddRequest.isValveStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetValveTestResponse>>> getValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting valve test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return valveTestRepository.findByCustomQuery(getCustomQueryValveTest(request, userDetails));
                    } else {
                        return valveTestRepository.findByCustomQuery(getCustomQueryValveTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(valveTestData -> Mono.just(valveTestRepository.countByCustomQuery(getCustomCountQueryValveTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetValveTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetValveTestResponse.builder()
                                        .valveTests(getValveDtoFromEntity(valveTestData))
                                        .totalRecords(total)
//                                        .totalFailed(valveTestRepository.countByCustomQuery(
//                                                getCustomQueryForValveFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Valve Tests")));
    }

    private TypedQuery<Long> getCustomQueryForValveFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM ValveTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addPcbTest(PcbTestAddRequest pcbTestAddRequest) {
        return Mono.just(pcbTestAddRequest)
                .map(device -> toPcbTest(pcbTestAddRequest))
                .map(pcbTestRepository::save)
                .map(pcbTest -> ResponseEntity.ok(CommonResponse.builder().message("Pcb test added successfully").status("SUCCESS").build()));
    }

    private PcbTestData toPcbTest(PcbTestAddRequest pcbTestAddRequest) {
        return PcbTestData.builder()
                .serialNumber(pcbTestAddRequest.getSerialNumber())
                .chargePortConnectStatus(pcbTestAddRequest.isChargePortConnectStatus())
                .intensityButtonStatus(pcbTestAddRequest.isIntensityButtonStatus())
                .runPauseButtonStatus(pcbTestAddRequest.isRunPauseButtonStatus())
                .modeButtonStatus(pcbTestAddRequest.isModeButtonStatus())
                .greenLedStatus(pcbTestAddRequest.isGreenLedStatus())
                .blueLedStatus(pcbTestAddRequest.isBlueLedStatus())
                .redLedRingStatus(pcbTestAddRequest.isRedLedRingStatus())
                .greenLedRingStatus(pcbTestAddRequest.isGreenLedRingStatus())
                .blueLedRingStatus(pcbTestAddRequest.isBlueLedRingStatus())
                .whiteLedRingStatus(pcbTestAddRequest.isWhiteLedRingStatus())
                .ledRingOffStatus(pcbTestAddRequest.isLedRingOffStatus())
                .buzzerStatus(pcbTestAddRequest.isBuzzerStatus())
                .latchSwitchLedOnStatus(pcbTestAddRequest.isLatchSwitchLedOnStatus())
                .latchSwitchOffStatus(pcbTestAddRequest.isLatchSwitchOffStatus())
                .latchSwitchLedOffStatus(pcbTestAddRequest.isLatchSwitchLedOffStatus())
                .latchSwitchOnStatus(pcbTestAddRequest.isLatchSwitchOnStatus())
                .chargerPortDisconnectStatus(pcbTestAddRequest.isChargerPortDisconnectStatus())
                .pumpStatus(pcbTestAddRequest.isPumpStatus())
                .batteryChargingStatus(pcbTestAddRequest.isBatteryChargingStatus())
                .startBatteryChargingPercentage(pcbTestAddRequest.getStartBatteryChargingPercentage())
                .endBatteryChargingPercentage(pcbTestAddRequest.getEndBatteryChargingPercentage())
                .batteryTemperatureStatus(pcbTestAddRequest.isBatteryTemperatureStatus())
                .batteryTemperature(pcbTestAddRequest.getBatteryTemperature())
                .pressurePathStatus(pcbTestAddRequest.isPressurePathStatus())
                .startPressurePathValue(pcbTestAddRequest.getStartPressurePathValue())
                .endPressurePathValue(pcbTestAddRequest.getEndPressurePathValue())
                .pressureSensorStatus(pcbTestAddRequest.isPressureSensorStatus())
                .startPressureSensorValue(pcbTestAddRequest.getStartPressureSensorValue())
                .endPressureSensorValue(pcbTestAddRequest.getEndPressureSensorValue())
                .valve01Status(pcbTestAddRequest.isValve01Status())
                .valve01StartValue(pcbTestAddRequest.getValve01StartValue())
                .valve01EndValue(pcbTestAddRequest.getValve01EndValue())
                .valve02Status(pcbTestAddRequest.isValve02Status())
                .valve02StartValue(pcbTestAddRequest.getValve02StartValue())
                .valve02EndValue(pcbTestAddRequest.getValve02EndValue())
                .valve03Status(pcbTestAddRequest.isValve03Status())
                .valve03StartValue(pcbTestAddRequest.getValve03StartValue())
                .valve03EndValue(pcbTestAddRequest.getValve03EndValue())
                .valve04Status(pcbTestAddRequest.isValve04Status())
                .valve04StartValue(pcbTestAddRequest.getValve04StartValue())
                .valve04EndValue(pcbTestAddRequest.getValve04EndValue())
                .valve05Status(pcbTestAddRequest.isValve05Status())
                .valve05StartValue(pcbTestAddRequest.getValve05StartValue())
                .valve05EndValue(pcbTestAddRequest.getValve05EndValue())
                .valve06Status(pcbTestAddRequest.isValve06Status())
                .valve06StartValue(pcbTestAddRequest.getValve06StartValue())
                .valve06EndValue(pcbTestAddRequest.getValve06EndValue())
                .phaseTwoValve01Status(pcbTestAddRequest.isPhaseTwoValve01Status())
                .phaseTwoValve01StartValue(pcbTestAddRequest.getPhaseTwoValve01StartValue())
                .phaseTwoValve01EndValue(pcbTestAddRequest.getPhaseTwoValve01EndValue())
                .phaseTwoValve02Status(pcbTestAddRequest.isPhaseTwoValve02Status())
                .phaseTwoValve02StartValue(pcbTestAddRequest.getPhaseTwoValve02StartValue())
                .phaseTwoValve02EndValue(pcbTestAddRequest.getPhaseTwoValve02EndValue())
                .phaseTwoValve03Status(pcbTestAddRequest.isPhaseTwoValve03Status())
                .phaseTwoValve03StartValue(pcbTestAddRequest.getPhaseTwoValve03StartValue())
                .phaseTwoValve03EndValue(pcbTestAddRequest.getPhaseTwoValve03EndValue())
                .phaseTwoValve04Status(pcbTestAddRequest.isPhaseTwoValve04Status())
                .phaseTwoValve04StartValue(pcbTestAddRequest.getPhaseTwoValve04StartValue())
                .phaseTwoValve04EndValue(pcbTestAddRequest.getPhaseTwoValve04EndValue())
                .phaseTwoValve05Status(pcbTestAddRequest.isPhaseTwoValve05Status())
                .phaseTwoValve05StartValue(pcbTestAddRequest.getPhaseTwoValve05StartValue())
                .phaseTwoValve05EndValue(pcbTestAddRequest.getPhaseTwoValve05EndValue())
                .phaseTwoValve06Status(pcbTestAddRequest.isPhaseTwoValve06Status())
                .phaseTwoValve06StartValue(pcbTestAddRequest.getPhaseTwoValve06StartValue())
                .phaseTwoValve06EndValue(pcbTestAddRequest.getPhaseTwoValve06EndValue())
                .phaseTwoPressureSensorStatus(pcbTestAddRequest.isPhaseTwoPressureSensorStatus())
                .phaseTwoPumpStatus(pcbTestAddRequest.isPhaseTwoPumpStatus())
                .dateTime(LocalDateTime.now())
                .status(pcbTestAddRequest.isChargePortConnectStatus() &&
                        pcbTestAddRequest.isIntensityButtonStatus() &&
                        pcbTestAddRequest.isRunPauseButtonStatus() &&
                        pcbTestAddRequest.isModeButtonStatus() &&
                        pcbTestAddRequest.isGreenLedStatus() &&
                        pcbTestAddRequest.isBlueLedStatus() &&
                        pcbTestAddRequest.isRedLedRingStatus() &&
                        pcbTestAddRequest.isGreenLedRingStatus() &&
                        pcbTestAddRequest.isBlueLedRingStatus() &&
                        pcbTestAddRequest.isWhiteLedRingStatus() &&
                        pcbTestAddRequest.isLedRingOffStatus() &&
                        pcbTestAddRequest.isBuzzerStatus() &&
                        pcbTestAddRequest.isLatchSwitchLedOnStatus() &&
                        pcbTestAddRequest.isLatchSwitchOffStatus() &&
                        pcbTestAddRequest.isLatchSwitchLedOffStatus() &&
                        pcbTestAddRequest.isLatchSwitchOnStatus() &&
                        pcbTestAddRequest.isChargerPortDisconnectStatus() &&
                        pcbTestAddRequest.isPumpStatus() &&
                        pcbTestAddRequest.isBatteryChargingStatus() &&
                        pcbTestAddRequest.isBatteryTemperatureStatus() &&
                        pcbTestAddRequest.isPressurePathStatus() &&
                        pcbTestAddRequest.isPressureSensorStatus() &&
                        pcbTestAddRequest.isValve01Status() &&
                        pcbTestAddRequest.isValve02Status() &&
                        pcbTestAddRequest.isValve03Status() &&
                        pcbTestAddRequest.isValve04Status() &&
                        pcbTestAddRequest.isValve05Status() &&
                        pcbTestAddRequest.isValve06Status() &&
                        pcbTestAddRequest.isPhaseTwoValve01Status() &&
                        pcbTestAddRequest.isPhaseTwoValve02Status() &&
                        pcbTestAddRequest.isPhaseTwoValve03Status() &&
                        pcbTestAddRequest.isPhaseTwoValve04Status() &&
                        pcbTestAddRequest.isPhaseTwoValve05Status() &&
                        pcbTestAddRequest.isPhaseTwoValve06Status() &&
                        pcbTestAddRequest.isPhaseTwoPressureSensorStatus() &&
                        pcbTestAddRequest.isPhaseTwoPumpStatus())
                .build();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetPcbTestResponse>>> getPcbTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting pcb test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return pcbTestRepository.findByCustomQuery(getCustomQueryPcbTest(request, userDetails));
                    } else {
                        return pcbTestRepository.findByCustomQuery(getCustomQueryPcbTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(pcbTestData -> Mono.just(pcbTestRepository.countByCustomQuery(getCustomCountQueryPcbTest(request, userDetails)))
                        .map(total -> ApiResponse.<GetPcbTestResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetPcbTestResponse.builder()
                                        .pcbTests(getPcbDtoFromEntity(pcbTestData))
                                        .totalRecords(total)
//                                        .totalFailed(pcbTestRepository.countByCustomQuery(
//                                                getCustomQueryForPcbFailedTests(request)
//                                        ))
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Pcb Tests")));
    }

    private TypedQuery<Long> getCustomQueryForPcbFailedTests(GetByPatternRequest request) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM PcbTestData v");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, null);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" AND v.status = false").toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<PcbTestDto> getPcbDtoFromEntity(List<PcbTestData> pcbTestData) {
        return pcbTestData.stream()
                .map(pcbTest -> PcbTestDto.builder()
                        .testId(pcbTest.getTestId())
                        .serialNumber(pcbTest.getSerialNumber())
                        .chargePortConnectStatus(pcbTest.isChargePortConnectStatus())
                        .intensityButtonStatus(pcbTest.isIntensityButtonStatus())
                        .runPauseButtonStatus(pcbTest.isRunPauseButtonStatus())
                        .modeButtonStatus(pcbTest.isModeButtonStatus())
                        .greenLedStatus(pcbTest.isGreenLedStatus())
                        .blueLedStatus(pcbTest.isBlueLedStatus())
                        .redLedRingStatus(pcbTest.isRedLedRingStatus())
                        .greenLedRingStatus(pcbTest.isGreenLedRingStatus())
                        .blueLedRingStatus(pcbTest.isBlueLedRingStatus())
                        .whiteLedRingStatus(pcbTest.isWhiteLedRingStatus())
                        .ledRingOffStatus(pcbTest.isLedRingOffStatus())
                        .buzzerStatus(pcbTest.isBuzzerStatus())
                        .latchSwitchLedOnStatus(pcbTest.isLatchSwitchLedOnStatus())
                        .latchSwitchOffStatus(pcbTest.isLatchSwitchOffStatus())
                        .latchSwitchLedOffStatus(pcbTest.isLatchSwitchLedOffStatus())
                        .latchSwitchOnStatus(pcbTest.isLatchSwitchOnStatus())
                        .chargerPortDisconnectStatus(pcbTest.isChargerPortDisconnectStatus())
                        .pumpStatus(pcbTest.isPumpStatus())
                        .batteryChargingStatus(pcbTest.isBatteryChargingStatus())
                        .startBatteryChargingPercentage(pcbTest.getStartBatteryChargingPercentage())
                        .endBatteryChargingPercentage(pcbTest.getEndBatteryChargingPercentage())
                        .batteryTemperatureStatus(pcbTest.isBatteryTemperatureStatus())
                        .batteryTemperature(pcbTest.getBatteryTemperature())
                        .pressurePathStatus(pcbTest.isPressurePathStatus())
                        .startPressurePathValue(pcbTest.getStartPressurePathValue())
                        .endPressurePathValue(pcbTest.getEndPressurePathValue())
                        .pressureSensorStatus(pcbTest.isPressureSensorStatus())
                        .startPressureSensorValue(pcbTest.getStartPressureSensorValue())
                        .endPressureSensorValue(pcbTest.getEndPressureSensorValue())
                        .valve01Status(pcbTest.isValve01Status())
                        .valve01StartValue(pcbTest.getValve01StartValue())
                        .valve01EndValue(pcbTest.getValve01EndValue())
                        .valve02Status(pcbTest.isValve02Status())
                        .valve02StartValue(pcbTest.getValve02StartValue())
                        .valve02EndValue(pcbTest.getValve02EndValue())
                        .valve03Status(pcbTest.isValve03Status())
                        .valve03StartValue(pcbTest.getValve03StartValue())
                        .valve03EndValue(pcbTest.getValve03EndValue())
                        .valve04Status(pcbTest.isValve04Status())
                        .valve04StartValue(pcbTest.getValve04StartValue())
                        .valve04EndValue(pcbTest.getValve04EndValue())
                        .valve05Status(pcbTest.isValve05Status())
                        .valve05StartValue(pcbTest.getValve05StartValue())
                        .valve05EndValue(pcbTest.getValve05EndValue())
                        .valve06Status(pcbTest.isValve06Status())
                        .valve06StartValue(pcbTest.getValve06StartValue())
                        .valve06EndValue(pcbTest.getValve06EndValue())
                        .phaseTwoValve01Status(pcbTest.isPhaseTwoValve01Status())
                        .phaseTwoValve01StartValue(pcbTest.getPhaseTwoValve01StartValue())
                        .phaseTwoValve01EndValue(pcbTest.getPhaseTwoValve01EndValue())
                        .phaseTwoValve02Status(pcbTest.isPhaseTwoValve02Status())
                        .phaseTwoValve02StartValue(pcbTest.getPhaseTwoValve02StartValue())
                        .phaseTwoValve02EndValue(pcbTest.getPhaseTwoValve02EndValue())
                        .phaseTwoValve03Status(pcbTest.isPhaseTwoValve03Status())
                        .phaseTwoValve03StartValue(pcbTest.getPhaseTwoValve03StartValue())
                        .phaseTwoValve03EndValue(pcbTest.getPhaseTwoValve03EndValue())
                        .phaseTwoValve04Status(pcbTest.isPhaseTwoValve04Status())
                        .phaseTwoValve04StartValue(pcbTest.getPhaseTwoValve04StartValue())
                        .phaseTwoValve04EndValue(pcbTest.getPhaseTwoValve04EndValue())
                        .phaseTwoValve05Status(pcbTest.isPhaseTwoValve05Status())
                        .phaseTwoValve05StartValue(pcbTest.getPhaseTwoValve05StartValue())
                        .phaseTwoValve05EndValue(pcbTest.getPhaseTwoValve05EndValue())
                        .phaseTwoValve06Status(pcbTest.isPhaseTwoValve06Status())
                        .phaseTwoValve06StartValue(pcbTest.getPhaseTwoValve06StartValue())
                        .phaseTwoValve06EndValue(pcbTest.getPhaseTwoValve06EndValue())
                        .phaseTwoPressureSensorStatus(pcbTest.isPhaseTwoPressureSensorStatus())
                        .phaseTwoPumpStatus(pcbTest.isPhaseTwoPumpStatus())
                        .status(pcbTest.getStatus())
                        .dateTime(pcbTest.getDateTime())
                        .build())
                .toList();
    }

    private TypedQuery<Long> getCustomCountQueryPcbTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM PcbTestData v");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<PcbTestData> getCustomQueryPcbTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM PcbTestData v");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<PcbTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), PcbTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<ValveTestDto> getValveDtoFromEntity(List<ValveTestData> valveTestData) {
        return valveTestData.stream()
                .map(valveTest -> ValveTestDto.builder()
                        .testId(valveTest.getTestId())
                        .deviceId(valveTest.getDevice().getDeviceId())
                        .qrCode(valveTest.getQrCode())
                        .airChamberLoadingPressure(valveTest.getAirChamberLoadingPressure())
                        .airChamberStatus(valveTest.isAirChamberStatus())
                        .v1OutletPressureAfter10MsOnTime(valveTest.getV1OutletPressureAfter10MsOnTime())
                        .v1OutletOnStatus(valveTest.isV1OutletOnStatus())
                        .v1OutletPressureAfter10MsOffTime(valveTest.getV1OutletPressureAfter10MsOffTime())
                        .v1OutletOffStatus(valveTest.isV1OutletOffStatus())
                        .v2OutletPressureAfter10MsOnTime(valveTest.getV2OutletPressureAfter10MsOnTime())
                        .v2OutletOnStatus(valveTest.isV2OutletOnStatus())
                        .v2OutletPressureAfter10MsOffTime(valveTest.getV2OutletPressureAfter10MsOffTime())
                        .v2OutletOffStatus(valveTest.isV2OutletOffStatus())
                        .v3OutletPressureAfter10MsOnTime(valveTest.getV3OutletPressureAfter10MsOnTime())
                        .v3OutletOnStatus(valveTest.isV3OutletOnStatus())
                        .v3OutletPressureAfter10MsOffTime(valveTest.getV3OutletPressureAfter10MsOffTime())
                        .v3OutletOffStatus(valveTest.isV3OutletOffStatus())
                        .valveStatus(valveTest.isValveStatus())
                        .status(valveTest.isValveStatus())
                        .dateTime(valveTest.getDateTime())
                        .build())
                .toList();
    }

    private TypedQuery<Long> getCustomCountQueryValveTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM ValveTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<ValveTestData> getCustomQueryValveTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM ValveTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<ValveTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), ValveTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<LatchButtonTestDto> getLatchButtonDtoFromEntity(List<LatchButtonTestData> latchButtonTestData) {
        return latchButtonTestData.stream()
                .map(latchButtonTest -> LatchButtonTestDto.builder()
                        .testId(latchButtonTest.getTestId())
                        .qrCode(latchButtonTest.getQrCode())
                        .deviceMac(latchButtonTest.getDevice().getDeviceMac())
                        .buttonOnTestStatus(latchButtonTest.getButtonOnTestStatus())
                        .buttonOffTestStatus(latchButtonTest.getButtonOffTestStatus())
                        .ledOnTestStatus(latchButtonTest.getLedOnTestStatus())
                        .latchButtonStatus(latchButtonTest.getLatchButtonStatus())
                        .dateTime(latchButtonTest.getDateTime())
                        .build())
                .toList();
    }

    private TypedQuery<Long> getCustomCountQueryLatchButtonTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM LatchButtonTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<LatchButtonTestData> getCustomQueryLatchButtonTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM LatchButtonTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<LatchButtonTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), LatchButtonTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<OverPressureValveTestDto> getOverPressureValveDtoFromEntity(List<OverPressureValveTestData> overPressureValveTestData) {
        return overPressureValveTestData.stream()
                .map(overPressureValveTest -> OverPressureValveTestDto.builder()
                        .testId(overPressureValveTest.getTestId())
                        .deviceMac(overPressureValveTest.getDevice().getDeviceMac())
                        .qrCode(overPressureValveTest.getQrCode())
                        .maxPressure(overPressureValveTest.getMaxPressure())
                        .maxPressureTime(overPressureValveTest.getMaxPressureTime())
                        .maxPressureFlowRate(overPressureValveTest.getMaxPressureFlowRate())
                        .normalPressure(overPressureValveTest.getNormalPressure())
                        .normalPressureTime(overPressureValveTest.getNormalPressureTime())
                        .normalPressureFlowRate(overPressureValveTest.getNormalPressureFlowRate())
                        .overPressureValveStatus(overPressureValveTest.getOverPressureValveStatus())
                        .status(overPressureValveTest.getStatus())
                        .dateTime(overPressureValveTest.getDateTime())
                        .build())
                .toList();
    }

    private TypedQuery<Long> getCustomCountQueryOverPressureTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM OverPressureValveTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<OverPressureValveTestData> getCustomQueryOverPressureTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM OverPressureValveTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<OverPressureValveTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), OverPressureValveTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<BatteryTestDto> getValueTestDtoFromEntity(List<BatteryTestData> valueTestData) {
        return valueTestData.stream()
                .map(valueTest -> BatteryTestDto.builder()
                        .testId(valueTest.getTestId())
                        .deviceMac(valueTest.getDevice().getDeviceMac())
                        .qrCode(valueTest.getQrCode())
                        .maximumCurrent(valueTest.getMaximumCurrent())
                        .maxCurrentDrawnTime(valueTest.getMaxCurrentDrawnTime())
                        .maxCurrentCutOff(valueTest.isMaxCurrentCutOff())
                        .normalCurrent(valueTest.getNormalCurrent())
                        .normalCurrentDrawnTime(valueTest.getNormalCurrentDrawnTime())
                        .normalCurrentCutOff(valueTest.isNormalCurrentCutOff())
                        .batteryStatus(valueTest.getStatus())
                        .dateTime(valueTest.getDateTime())
                        .build())
                .toList();
    }

    private TypedQuery<Long> getCustomCountQueryValueTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM BatteryTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<BatteryTestData> getCustomQueryValueTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM BatteryTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<BatteryTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), BatteryTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private List<AirPumpTestDto> getAirPumpDtoFromEntity(List<AirPumpTestData> airPumpTestData) {
        return airPumpTestData.stream()
                .map(airPumpTest -> AirPumpTestDto.builder()
                        .testId(airPumpTest.getTestId())
                        .deviceId(airPumpTest.getDevice().getDeviceId())
                        .idleVolLowTh(airPumpTest.getIdleVolLowTh())
                        .idleVolUpTh(airPumpTest.getIdleVolUpTh())
                        .idleCurUpTh(airPumpTest.getIdleCurUpTh())
                        .loadVolLowTh(airPumpTest.getLoadVolLowTh())
                        .loadVolUp(airPumpTest.getLoadVolUp())
                        .loadCurUpTh(airPumpTest.getLoadCurUpTh())
                        .setPressure(airPumpTest.getSetPressure())
                        .serialNumber(airPumpTest.getSerialNumber())
                        .idleVol(airPumpTest.getIdleVol())
                        .idleVolStatus(airPumpTest.getIdleVolStatus())
                        .idleCurrent(airPumpTest.getIdleCurrent())
                        .idleCurrentStatus(airPumpTest.getIdleCurrentStatus())
                        .loadVoltage(airPumpTest.getLoadVoltage())
                        .loadVoltageStatus(airPumpTest.getLoadVoltageStatus())
                        .loadCurrent(airPumpTest.getLoadCurrent())
                        .loadCurrentStatus(airPumpTest.getLoadCurrentStatus())
                        .maxPressure(airPumpTest.getMaxPressure())
                        .maxPressureStatus(airPumpTest.getMaxPressureStatus())
                        .noiseLevel(airPumpTest.getNoiseLevel())
                        .deviceStatus(airPumpTest.getDeviceStatus())
                        .status(airPumpTest.getStatus())
                        .dateTime(airPumpTest.getDateTime())
                        .build())
                .toList();
    }

    private BatteryTestData toValueTest(BatteryTestAddRequest batteryTestAddRequest, Device device) {
        return BatteryTestData.builder()
                .device(device)
                .qrCode(batteryTestAddRequest.getQrCode())
                .maximumCurrent(batteryTestAddRequest.getMaximumCurrent())
                .maxCurrentDrawnTime(batteryTestAddRequest.getMaxCurrentDrawnTime())
                .maxCurrentCutOff(batteryTestAddRequest.isMaxCurrentCutOff())
                .normalCurrent(batteryTestAddRequest.getNormalCurrent())
                .normalCurrentDrawnTime(batteryTestAddRequest.getNormalCurrentDrawnTime())
                .normalCurrentCutOff(batteryTestAddRequest.isNormalCurrentCutOff())
                .status(batteryTestAddRequest.getBatteryStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    private TypedQuery<AirPumpTestData> getCustomQueryAirPumpTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM AirPumpTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<AirPumpTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), AirPumpTestData.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<Long> getCustomCountQueryAirPumpTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM AirPumpTestData v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private static void calculateFilterParts(GetByPatternRequest request, List<String> filterParts, UserDetails userDetails) {
        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            switch (request.getFilterType()) {
                case "DEVICE_MAC" -> filterParts.add("d.deviceMac LIKE '%" + request.getFilterValue() + "%'");
                case "CREATED_BY" -> filterParts.add("createdBy LIKE '%" + request.getFilterValue() + "%'");
                case "TEST_ID" -> filterParts.add("serialNumber LIKE '%" + request.getFilterValue() + "%'");
            }
        }

        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            filterParts.add("v.dateTime > :startDate");
        }

        if (request.getToDate() != null && !request.getToDate().isEmpty()) {
            filterParts.add("v.dateTime < :endDate");
        }

        setStatusFilter(request, filterParts);
    }

    private static void setStatusFilter(GetByPatternRequest request, List<String> filterParts) {
        List<String> availableStatusList = List.of("PASS", "FAIL");
        if (request.getStatus() != null && !request.getStatus().isEmpty() && availableStatusList.contains(request.getStatus())) {
            boolean status = request.getStatus().equals("PASS");
            switch (request.getRequestType()) {
                case "AIR_PUMP", "POWER_PCB", "POWER_SUPPLY", "VALVE" -> filterParts.add("v.status = " + status);
            }
        }
    }

    private <T> TypedQuery<T> exchangeDateFilterInQuery(TypedQuery<T> query, GetByPatternRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            LocalDateTime lastDateStart = LocalDateTime.of(LocalDateTime.parse(request.getFromDate(), formatter).toLocalDate(), LocalTime.MIDNIGHT);
            query.setParameter("startDate", lastDateStart);
        }

        if (request.getToDate() != null && !request.getToDate().isEmpty()) {
            LocalDateTime lastDateEnd = LocalDateTime.of(LocalDateTime.parse(request.getToDate(), formatter).toLocalDate(), LocalTime.MAX);
            query.setParameter("endDate", lastDateEnd);
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
