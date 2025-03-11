package com.test_biotab.test_biotab_server.service.impl;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.*;
import com.test_biotab.test_biotab_server.entity.*;
import com.test_biotab.test_biotab_server.repository.*;
import com.test_biotab.test_biotab_server.service.DeviceService;
import com.test_biotab.test_biotab_server.service.TestService;
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

    private final PowerSupplyTestRepository powerSupplyTestRepository;
    private final ValveTestRepository valveTestRepository;
    private final AirPumpTestRepository airPumpTestRepository;
    private final PowerPCBTestRepository powerPCBTestRepository;
    private final AirPumpV2TestRepository airPumpV2TestRepository;
    private final PowerPCBV2TestRepository powerPCBV2TestRepository;
    private final PowerSupplyV2TestRepository powerSupplyV2TestRepository;
    private final OpValveTestRepository opValveTestRepository;
    private final ValveSequenceTestRepository valveSequenceTestRepository;
    private final ValveCardTestRepository valveCardTestRepository;
    private final ManiFoldLeakTestRepository maniFoldLeakTestRepository;
    private final UiPcbTestRepository uiPcbTestRepository;
    private final CableTestRepository cableTestRepository;
    private final FanTestRepository fanTestRepository;
    private final DeviceService deviceService;

    @PersistenceContext
    private EntityManager entityManager;

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
                .idleVoltageLowTh(powerSupplyTestAddRequest.getIdleVoltageLowTh())
                .idleVoltageUpTh(powerSupplyTestAddRequest.getIdleVoltageUpTh())
                .loadVoltageLowTh(powerSupplyTestAddRequest.getLoadVoltageLowTh())
                .loadVoltageUpTh(powerSupplyTestAddRequest.getLoadVoltageUpTh())
                .loadCurrentUpTh(powerSupplyTestAddRequest.getLoadCurrentUpTh())
                .serialNumber(powerSupplyTestAddRequest.getSerialNumber())
                .idleVol(powerSupplyTestAddRequest.getIdleVol())
                .idleVolStatus(powerSupplyTestAddRequest.getIdleVolStatus())
                .loadVol(powerSupplyTestAddRequest.getLoadVol())
                .loadVolStatus(powerSupplyTestAddRequest.getLoadVolStatus())
                .loadCurrent(powerSupplyTestAddRequest.getLoadCurrent())
                .loadCurrentStatus(powerSupplyTestAddRequest.getLoadCurrentStatus())
                .operatingPower(powerSupplyTestAddRequest.getOperatingPower())
                .noiseLevel(powerSupplyTestAddRequest.getNoiseLevel())
                .status(powerSupplyTestAddRequest.getIdleVolStatus() && powerSupplyTestAddRequest.getLoadVolStatus() && powerSupplyTestAddRequest.getLoadCurrentStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerSupplyTestDto>>>> getPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting power supply test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return powerSupplyTestRepository.findByCustomQuery(getCustomQuery(PowerSupplyTestData.class, request, userDetails));
                    } else {
                        assert pageNo != null;
                        return powerSupplyTestRepository.findByCustomQuery(getCustomQuery(PowerSupplyTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(powerSupplyTestData -> Mono.just(valveTestRepository.countByCustomQuery(getCustomCountQuery(PowerSupplyTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<PowerSupplyTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<PowerSupplyTestDto>builder()
                                        .tests(getPowerSupplyDtoFromEntity(powerSupplyTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("Error getting power supply tests", e);
                    return Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Power Supply Tests"));
                });
    }

    private List<PowerSupplyTestDto> getPowerSupplyDtoFromEntity(List<PowerSupplyTestData> powerSupplyTestData) {
        return powerSupplyTestData.stream()
                .map(powerSupplyTest -> PowerSupplyTestDto.builder()
                        .testId(powerSupplyTest.getTestId())
                        .deviceId(powerSupplyTest.getDevice().getDeviceId())
                        .idleVolLowTh(powerSupplyTest.getIdleVoltageLowTh())
                        .idleVolUpTh(powerSupplyTest.getIdleVoltageUpTh())
                        .loadVolLowTh(powerSupplyTest.getLoadVoltageLowTh())
                        .loadVolUpTh(powerSupplyTest.getLoadVoltageUpTh())
                        .loadCurUpTh(powerSupplyTest.getLoadCurrentUpTh())
                        .serialNumber(powerSupplyTest.getSerialNumber())
                        .idleVol(powerSupplyTest.getIdleVol())
                        .idleVolStatus(powerSupplyTest.getIdleVolStatus())
                        .loadVol(powerSupplyTest.getLoadVol())
                        .loadVolStatus(powerSupplyTest.getLoadVolStatus())
                        .loadCurrent(powerSupplyTest.getLoadCurrent())
                        .loadCurrentStatus(powerSupplyTest.getLoadCurrentStatus())
                        .operatingPower(powerSupplyTest.getOperatingPower())
                        .noiseLevel(powerSupplyTest.getNoiseLevel())
                        .status(powerSupplyTest.getStatus())
                        .dateTime(powerSupplyTest.getDateTime())
                        .build())
                .toList();
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
                .idleVoltageLowThresh(valveTestAddRequest.getIdleVoltageLowThresh())
                .idleVoltageUpThresh(valveTestAddRequest.getIdleVoltageUpThresh())
                .idleCurrentUpThresh(valveTestAddRequest.getIdleCurrentUpThresh())
                .loadVoltageLowThresh(valveTestAddRequest.getLoadVoltageLowThresh())
                .loadVoltageUpThresh(valveTestAddRequest.getLoadVoltageUpThresh())
                .loadCurrentUpThresh(valveTestAddRequest.getLoadCurrentUpThresh())
                .setPressure(valveTestAddRequest.getSetPressure())
                .serialNumber(valveTestAddRequest.getSerialNumber())
                .idleVoltage(valveTestAddRequest.getIdleVoltage())
                .idleVoltageStatus(valveTestAddRequest.getIdleVoltageStatus())
                .idleCurrent(valveTestAddRequest.getIdleCurrent())
                .idleCurrentStatus(valveTestAddRequest.getIdleCurrentStatus())
                .coilResistance(valveTestAddRequest.getCoilResistance())
                .operatingCurrent(valveTestAddRequest.getOperatingCurrent())
                .peakPower(valveTestAddRequest.getPeakPower())
                .averagePower(valveTestAddRequest.getAveragePower())
                .flowRate(valveTestAddRequest.getFlowRate())
                .flowRateStatus(valveTestAddRequest.getFlowRateStatus())
                .status(valveTestAddRequest.getIdleVoltageStatus() && valveTestAddRequest.getIdleCurrentStatus() && valveTestAddRequest.getFlowRateStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveTestDto>>>> getValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting valve test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return valveTestRepository.findByCustomQuery(getCustomQuery(ValveTestData.class, request, userDetails));
                    } else {
                        assert pageNo != null;
                        return valveTestRepository.findByCustomQuery(getCustomQuery(ValveTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(valveTestData -> Mono.just(valveTestRepository.countByCustomQuery(getCustomCountQuery(ValveTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<ValveTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<ValveTestDto>builder()
                                        .tests(getValveDtoFromEntity(valveTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("Error getting valve tests", e);
                    return Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get valve Tests"));
                });
    }

    private List<ValveTestDto> getValveDtoFromEntity(List<ValveTestData> valveTestData) {
        return valveTestData.stream()
                .map(valveTest -> ValveTestDto.builder()
                        .testId(valveTest.getTestId())
                        .deviceId(valveTest.getDevice().getDeviceId())
                        .idleVoltageLowThresh(valveTest.getIdleVoltageLowThresh())
                        .idleVoltageUpThresh(valveTest.getIdleVoltageUpThresh())
                        .idleCurrentUpThresh(valveTest.getIdleCurrentUpThresh())
                        .loadVoltageLowThresh(valveTest.getLoadVoltageLowThresh())
                        .loadVoltageUpThresh(valveTest.getLoadVoltageUpThresh())
                        .loadCurrentUpThresh(valveTest.getLoadCurrentUpThresh())
                        .setPressure(valveTest.getSetPressure())
                        .serialNumber(valveTest.getSerialNumber())
                        .idleVoltage(valveTest.getIdleVoltage())
                        .idleVoltageStatus(valveTest.getIdleVoltageStatus())
                        .idleCurrent(valveTest.getIdleCurrent())
                        .idleCurrentStatus(valveTest.getIdleCurrentStatus())
                        .coilResistance(valveTest.getCoilResistance())
                        .operatingCurrent(valveTest.getOperatingCurrent())
                        .peakPower(valveTest.getPeakPower())
                        .averagePower(valveTest.getAveragePower())
                        .flowRate(valveTest.getFlowRate())
                        .flowRateStatus(valveTest.getFlowRateStatus())
                        .status(valveTest.getStatus())
                        .dateTime(valveTest.getDateTime())
                        .build())
                .toList();
    }

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
                .idleVoltageLowThresh(airPumpTestAddRequest.getIdleVoltageLowThresh())
                .idleVoltageUpThresh(airPumpTestAddRequest.getIdleVoltageUpThresh())
                .idleCurrentUpThresh(airPumpTestAddRequest.getIdleCurrentUpThresh())
                .loadVoltageLowThresh(airPumpTestAddRequest.getLoadVoltageLowThresh())
                .loadVoltageUpThresh(airPumpTestAddRequest.getLoadVoltageUpThresh())
                .loadCurrentUpThresh(airPumpTestAddRequest.getLoadCurrentUpThresh())
                .setPressure(airPumpTestAddRequest.getSetPressure())
                .serialNumber(airPumpTestAddRequest.getSerialNumber())
                .idleVoltage(airPumpTestAddRequest.getIdleVoltage())
                .idleVoltageStatus(airPumpTestAddRequest.getIdleVoltageStatus())
                .idleCurrent(airPumpTestAddRequest.getIdleCurrent())
                .idleCurrentStatus(airPumpTestAddRequest.getIdleCurrentStatus())
                .loadVoltage(airPumpTestAddRequest.getLoadVoltage())
                .loadVoltageStatus(airPumpTestAddRequest.getLoadVoltageStatus())
                .loadCurrent(airPumpTestAddRequest.getLoadCurrent())
                .loadCurrentStatus(airPumpTestAddRequest.getLoadCurrentStatus())
                .flowRate(airPumpTestAddRequest.getFlowRate())
                .flowRateStatus(airPumpTestAddRequest.getFlowRateStatus())
                .noiseLevelStatus(airPumpTestAddRequest.getNoiseLevelStatus())
                .status(airPumpTestAddRequest.getIdleVoltageStatus() && airPumpTestAddRequest.getIdleCurrentStatus() && airPumpTestAddRequest.getLoadVoltageStatus() && airPumpTestAddRequest.getLoadCurrentStatus() && airPumpTestAddRequest.getNoiseLevelStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<AirPumpTestDto>>>> getAirPumpTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting air pump test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return airPumpTestRepository.findByCustomQuery(getCustomQuery(AirPumpTestData.class, request, userDetails));
                    } else {
                        return airPumpTestRepository.findByCustomQuery(getCustomQuery(AirPumpTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(airPumpTestData -> Mono.just(powerPCBTestRepository.countByCustomQuery(getCustomCountQuery(AirPumpTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<AirPumpTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<AirPumpTestDto>builder()
                                        .tests(getAirPumpDtoFromEntity(airPumpTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Air Pump Tests")));

    }

    private List<AirPumpTestDto> getAirPumpDtoFromEntity(List<AirPumpTestData> airPumpTestData) {
        return airPumpTestData.stream()
                .map(airPumpTest -> AirPumpTestDto.builder()
                        .testId(airPumpTest.getTestId())
                        .deviceId(airPumpTest.getDevice().getDeviceId())
                        .idleVoltageLowThresh(airPumpTest.getIdleVoltageLowThresh())
                        .idleVoltageUpThresh(airPumpTest.getIdleVoltageUpThresh())
                        .idleCurrentUpThresh(airPumpTest.getIdleCurrentUpThresh())
                        .loadVoltageLowThresh(airPumpTest.getLoadVoltageLowThresh())
                        .loadVoltageUpThresh(airPumpTest.getLoadVoltageUpThresh())
                        .loadCurrentUpThresh(airPumpTest.getLoadCurrentUpThresh())
                        .setPressure(airPumpTest.getSetPressure())
                        .serialNumber(airPumpTest.getSerialNumber())
                        .idleVoltage(airPumpTest.getIdleVoltage())
                        .idleVoltageStatus(airPumpTest.getIdleVoltageStatus())
                        .idleCurrent(airPumpTest.getIdleCurrent())
                        .idleCurrentStatus(airPumpTest.getIdleCurrentStatus())
                        .loadVoltage(airPumpTest.getLoadVoltage())
                        .loadVoltageStatus(airPumpTest.getLoadVoltageStatus())
                        .loadCurrent(airPumpTest.getLoadCurrent())
                        .loadCurrentStatus(airPumpTest.getLoadCurrentStatus())
                        .flowRate(airPumpTest.getFlowRate())
                        .flowRateStatus(airPumpTest.getFlowRateStatus())
                        .noiseLevelStatus(airPumpTest.getNoiseLevelStatus())
                        .status(airPumpTest.getStatus())
                        .dateTime(airPumpTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addPowerPCBTest(PowerPCBTestAddRequest powerPCBTestAddRequest) {
        return Mono.just(powerPCBTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(powerPCBTestAddRequest.getDeviceMac())
                        .map(device -> toPowerPCPTest(powerPCBTestAddRequest, device))
                        .map(powerPCBTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(powerPCPTest -> ResponseEntity.ok(CommonResponse.builder().message("Power PCB test added successfully").status("SUCCESS").build()));
    }

    private PowerPCBTestData toPowerPCPTest(PowerPCBTestAddRequest powerPCBTestAddRequest, Device device) {
        return PowerPCBTestData.builder()
                .device(device)
                .powerGroundResistanceUpperLimit(powerPCBTestAddRequest.getPowerGroundResistanceUpperLimit())
                .serialNumber(powerPCBTestAddRequest.getSerialNumber())
                .dcBarrelJackConnectivityStatus(powerPCBTestAddRequest.getDcBarrelJackConnectivityStatus())
                .usbCPowerOutletConnectivity(powerPCBTestAddRequest.getUsbCPowerOutletConnectivity())
                .powerGroundResistance(powerPCBTestAddRequest.getPowerGroundResistance())
                .powerGroundResistanceStatus(powerPCBTestAddRequest.getPowerGroundResistanceStatus())
                .status(powerPCBTestAddRequest.getDcBarrelJackConnectivityStatus() && powerPCBTestAddRequest.getPowerGroundResistanceStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerPCBTestDto>>>> getPowerPCBTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting power pcb test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return powerPCBTestRepository.findByCustomQuery(getCustomQuery(PowerPCBTestData.class, request, userDetails));
                    } else {
                        return powerPCBTestRepository.findByCustomQuery(getCustomQuery(PowerPCBTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(powerPCBTestData -> Mono.just(powerPCBTestRepository.countByCustomQuery(getCustomCountQuery(PowerPCBTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<PowerPCBTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<PowerPCBTestDto>builder()
                                        .tests(getPowerPCPDtoFromEntity(powerPCBTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Power PCB Tests")));
    }

    private List<PowerPCBTestDto> getPowerPCPDtoFromEntity(List<PowerPCBTestData> powerPCBTestData) {
        return powerPCBTestData.stream()
                .map(powerPCPTest -> PowerPCBTestDto.builder()
                        .testId(powerPCPTest.getTestId())
                        .deviceId(powerPCPTest.getDevice().getDeviceId())
                        .powerGroundResistanceUpperLimit(powerPCPTest.getPowerGroundResistanceUpperLimit())
                        .serialNumber(powerPCPTest.getSerialNumber())
                        .dcBarrelJackConnectivityStatus(powerPCPTest.getDcBarrelJackConnectivityStatus())
                        .usbCPowerOutletConnectivity(powerPCPTest.getUsbCPowerOutletConnectivity())
                        .powerGroundResistance(powerPCPTest.getPowerGroundResistance())
                        .powerGroundResistanceStatus(powerPCPTest.getPowerGroundResistanceStatus())
                        .status(powerPCPTest.getStatus())
                        .dateTime(powerPCPTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addAirPumpV2Test(AirPumpV2TestAddRequest airPumpV2TestAddRequest) {
        return Mono.just(airPumpV2TestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(airPumpV2TestAddRequest.getDeviceMac())
                        .map(device -> toAirPumpV2Test(airPumpV2TestAddRequest, device))
                        .map(airPumpV2TestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(airPumpV2Test -> ResponseEntity.ok(CommonResponse.builder().message("Air pump v2 test added successfully").status("SUCCESS").build()));
    }

    private AirPumpV2TestData toAirPumpV2Test(AirPumpV2TestAddRequest airPumpV2TestAddRequest, Device device) {
        return AirPumpV2TestData.builder()
                .device(device)
                .flowRateLowThresh(airPumpV2TestAddRequest.getFlowRateLowThresh())
                .flowRateUpThresh(airPumpV2TestAddRequest.getFlowRateUpThresh())
                .loadVoltageLowThresh(airPumpV2TestAddRequest.getLoadVoltageLowThresh())
                .loadVoltageUpThresh(airPumpV2TestAddRequest.getLoadVoltageUpThresh())
                .loadCurrentUpThresh(airPumpV2TestAddRequest.getLoadCurrentUpThresh())
                .pressureLowThresh(airPumpV2TestAddRequest.getPressureLowThresh())
                .pressureUpThresh(airPumpV2TestAddRequest.getPressureUpThresh())
                .pressure(airPumpV2TestAddRequest.getPressure())
                .pressureStatus(airPumpV2TestAddRequest.getPressureStatus())
                .serialNumber(airPumpV2TestAddRequest.getSerialNumber())
                .flowRate(airPumpV2TestAddRequest.getFlowRate())
                .flowRateStatus(airPumpV2TestAddRequest.getFlowRateStatus())
                .loadVoltage(airPumpV2TestAddRequest.getLoadVoltage())
                .loadVoltageStatus(airPumpV2TestAddRequest.getLoadVoltageStatus())
                .loadCurrent(airPumpV2TestAddRequest.getLoadCurrent())
                .loadCurrentStatus(airPumpV2TestAddRequest.getLoadCurrentStatus())
                .noiseLevelStatus(airPumpV2TestAddRequest.getNoiseLevelStatus())
                .status(airPumpV2TestAddRequest.getFlowRateStatus()
                        && airPumpV2TestAddRequest.getPressureStatus()
                        && airPumpV2TestAddRequest.getLoadVoltageStatus()
                        && airPumpV2TestAddRequest.getLoadCurrentStatus()
                        && airPumpV2TestAddRequest.getNoiseLevelStatus())
                .dateTime(LocalDateTime.now())
                .build();

    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<AirPumpV2TestDto>>>> getAirPumpV2Test(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting air pump v2 test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return airPumpV2TestRepository.findByCustomQuery(getCustomQuery(AirPumpV2TestData.class, request, userDetails));
                    } else {
                        return airPumpV2TestRepository.findByCustomQuery(getCustomQuery(AirPumpV2TestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(airPumpV2TestData -> Mono.just(airPumpV2TestRepository.countByCustomQuery(getCustomCountQuery(AirPumpV2TestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<AirPumpV2TestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<AirPumpV2TestDto>builder()
                                        .tests(getAirPumpV2DtoFromEntity(airPumpV2TestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Air Pump V2 Tests")));
    }

    private List<AirPumpV2TestDto> getAirPumpV2DtoFromEntity(List<AirPumpV2TestData> airPumpV2TestData) {
        return airPumpV2TestData.stream()
                .map(airPumpV2Test -> AirPumpV2TestDto.builder()
                        .testId(airPumpV2Test.getTestId())
                        .deviceId(airPumpV2Test.getDevice().getDeviceId())
                        .flowRateLowThresh(airPumpV2Test.getFlowRateLowThresh())
                        .flowRateUpThresh(airPumpV2Test.getFlowRateUpThresh())
                        .loadVoltageLowThresh(airPumpV2Test.getLoadVoltageLowThresh())
                        .loadVoltageUpThresh(airPumpV2Test.getLoadVoltageUpThresh())
                        .loadCurrentUpThresh(airPumpV2Test.getLoadCurrentUpThresh())
                        .pressureLowThresh(airPumpV2Test.getPressureLowThresh())
                        .pressureUpThresh(airPumpV2Test.getPressureUpThresh())
                        .pressure(airPumpV2Test.getPressure())
                        .pressureStatus(airPumpV2Test.getPressureStatus())
                        .serialNumber(airPumpV2Test.getSerialNumber())
                        .flowRate(airPumpV2Test.getFlowRate())
                        .flowRateStatus(airPumpV2Test.getFlowRateStatus())
                        .loadVoltage(airPumpV2Test.getLoadVoltage())
                        .loadVoltageStatus(airPumpV2Test.getLoadVoltageStatus())
                        .loadCurrent(airPumpV2Test.getLoadCurrent())
                        .loadCurrentStatus(airPumpV2Test.getLoadCurrentStatus())
                        .noiseLevelStatus(airPumpV2Test.getNoiseLevelStatus())
                        .status(airPumpV2Test.getStatus())
                        .dateTime(airPumpV2Test.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addPowerPCBV2Test(PowerPCBV2TestAddRequest powerPCBV2TestAddRequest) {
        return Mono.just(powerPCBV2TestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(powerPCBV2TestAddRequest.getDeviceMac())
                        .map(device -> toPowerPCBV2Test(powerPCBV2TestAddRequest, device))
                        .map(powerPCBV2TestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(powerPCPTest -> ResponseEntity.ok(CommonResponse.builder().message("Power PCB v2 test added successfully").status("SUCCESS").build()));
    }

    private PowerPCBV2TestData toPowerPCBV2Test(PowerPCBV2TestAddRequest powerPCBV2TestAddRequest, Device device) {
        return PowerPCBV2TestData.builder()
                .device(device)
                .loadVoltageLowThresh(powerPCBV2TestAddRequest.getLoadVoltageLowThresh())
                .serialNumber(powerPCBV2TestAddRequest.getSerialNumber())
                .usbCPowerOutletConnectivity(powerPCBV2TestAddRequest.getUsbCPowerOutletConnectivity())
                .loadVoltage(powerPCBV2TestAddRequest.getLoadVoltage())
                .loadVoltageStatus(powerPCBV2TestAddRequest.getLoadVoltageStatus())
                .loadCurrentStatus(powerPCBV2TestAddRequest.getLoadCurrentStatus())
                .loadCurrentLowThresh(powerPCBV2TestAddRequest.getLoadCurrentLowThresh())
                .loadCurrent(powerPCBV2TestAddRequest.getLoadCurrent())
                .deviceStatus(powerPCBV2TestAddRequest.getDeviceStatus())
                .noiseLevelStatus(powerPCBV2TestAddRequest.getNoiseLevelStatus())
                .status(powerPCBV2TestAddRequest.getLoadVoltageStatus()
                        && powerPCBV2TestAddRequest.getLoadCurrentStatus()
                        && powerPCBV2TestAddRequest.getNoiseLevelStatus()
                        && powerPCBV2TestAddRequest.getDeviceStatus()
                        && (!powerPCBV2TestAddRequest.getUsbCPowerOutletConnectivity().equalsIgnoreCase("false"))
                )
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerPCBV2TestDto>>>> getPowerPCBV2Test(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting power pcb v2 test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return powerPCBV2TestRepository.findByCustomQuery(getCustomQuery(PowerPCBV2TestData.class, request, userDetails));
                    } else {
                        return powerPCBV2TestRepository.findByCustomQuery(getCustomQuery(PowerPCBV2TestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(powerPCBV2TestData -> Mono.just(powerPCBV2TestRepository.countByCustomQuery(getCustomCountQuery(PowerPCBV2TestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<PowerPCBV2TestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<PowerPCBV2TestDto>builder()
                                        .tests(getPowerPCBV2DtoFromEntity(powerPCBV2TestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Power PCB V2 Tests")));
    }

    private List<PowerPCBV2TestDto> getPowerPCBV2DtoFromEntity(List<PowerPCBV2TestData> powerPCBV2TestData) {
        return powerPCBV2TestData.stream()
                .map(powerPCBV2Test -> PowerPCBV2TestDto.builder()
                        .testId(powerPCBV2Test.getTestId())
                        .deviceId(powerPCBV2Test.getDevice().getDeviceId())
                        .loadVoltageLowThresh(powerPCBV2Test.getLoadVoltageLowThresh())
                        .serialNumber(powerPCBV2Test.getSerialNumber())
                        .usbCPowerOutletConnectivity(powerPCBV2Test.getUsbCPowerOutletConnectivity())
                        .loadVoltage(powerPCBV2Test.getLoadVoltage())
                        .loadVoltageStatus(powerPCBV2Test.getLoadVoltageStatus())
                        .loadCurrentStatus(powerPCBV2Test.getLoadCurrentStatus())
                        .loadCurrentLowThresh(powerPCBV2Test.getLoadCurrentLowThresh())
                        .loadCurrent(powerPCBV2Test.getLoadCurrent())
                        .deviceStatus(powerPCBV2Test.getDeviceStatus())
                        .noiseLevelStatus(powerPCBV2Test.getNoiseLevelStatus())
                        .status(powerPCBV2Test.getStatus())
                        .dateTime(powerPCBV2Test.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addPowerSupplyV2Test(PowerSupplyV2TestAddRequest powerSupplyV2TestAddRequest) {
        return Mono.just(powerSupplyV2TestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(powerSupplyV2TestAddRequest.getDeviceMac())
                        .map(device -> toPowerSupplyV2Test(powerSupplyV2TestAddRequest, device))
                        .map(powerSupplyV2TestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(powerSupplyV2Test -> ResponseEntity.ok(CommonResponse.builder().message("Power supply v2 test added successfully").status("SUCCESS").build()));
    }

    private PowerSupplyV2TestData toPowerSupplyV2Test(PowerSupplyV2TestAddRequest powerSupplyV2TestAddRequest, Device device) {
        return PowerSupplyV2TestData.builder()
                .device(device)
                .idleVoltageLowTh(powerSupplyV2TestAddRequest.getIdleVoltageLowTh())
                .idleVoltageUpTh(powerSupplyV2TestAddRequest.getIdleVoltageUpTh())
                .loadVoltageLowTh(powerSupplyV2TestAddRequest.getLoadVoltageLowTh())
                .loadVoltageUpTh(powerSupplyV2TestAddRequest.getLoadVoltageUpTh())
                .loadCurrentUpTh(powerSupplyV2TestAddRequest.getLoadCurrentUpTh())
                .serialNumber(powerSupplyV2TestAddRequest.getSerialNumber())
                .idleVol(powerSupplyV2TestAddRequest.getIdleVol())
                .idleVolStatus(powerSupplyV2TestAddRequest.getIdleVolStatus())
                .loadVol(powerSupplyV2TestAddRequest.getLoadVol())
                .loadVolStatus(powerSupplyV2TestAddRequest.getLoadVolStatus())
                .loadCurrent(powerSupplyV2TestAddRequest.getLoadCurrent())
                .loadCurrentStatus(powerSupplyV2TestAddRequest.getLoadCurrentStatus())
                .operatingPower(powerSupplyV2TestAddRequest.getOperatingPower())
                .noiseLevel(powerSupplyV2TestAddRequest.getNoiseLevel())
                .status(powerSupplyV2TestAddRequest.getIdleVolStatus()
                        && powerSupplyV2TestAddRequest.getLoadVolStatus()
                        && powerSupplyV2TestAddRequest.getLoadCurrentStatus())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<PowerSupplyV2TestDto>>>> getPowerSupplyV2Test(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting power supply v2 test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return powerSupplyV2TestRepository.findByCustomQuery(getCustomQuery(PowerSupplyV2TestData.class, request, userDetails));
                    } else {
                        return powerSupplyV2TestRepository.findByCustomQuery(getCustomQuery(PowerSupplyV2TestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(powerSupplyV2TestData -> Mono.just(valveSequenceTestRepository.countByCustomQuery(getCustomCountQuery(PowerSupplyV2TestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<PowerSupplyV2TestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<PowerSupplyV2TestDto>builder()
                                        .tests(getPowerSupplyV2DtoFromEntity(powerSupplyV2TestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Power Supply V2 Tests")));
    }

    private List<PowerSupplyV2TestDto> getPowerSupplyV2DtoFromEntity(List<PowerSupplyV2TestData> powerSupplyV2TestData) {
        return powerSupplyV2TestData.stream()
                .map(powerSupplyV2Test -> PowerSupplyV2TestDto.builder()
                        .testId(powerSupplyV2Test.getTestId())
                        .deviceId(powerSupplyV2Test.getDevice().getDeviceId())
                        .idleVoltageLowTh(powerSupplyV2Test.getIdleVoltageLowTh())
                        .idleVoltageUpTh(powerSupplyV2Test.getIdleVoltageUpTh())
                        .loadVoltageLowTh(powerSupplyV2Test.getLoadVoltageLowTh())
                        .loadVoltageUpTh(powerSupplyV2Test.getLoadVoltageUpTh())
                        .loadCurrentUpTh(powerSupplyV2Test.getLoadCurrentUpTh())
                        .serialNumber(powerSupplyV2Test.getSerialNumber())
                        .idleVol(powerSupplyV2Test.getIdleVol())
                        .idleVolStatus(powerSupplyV2Test.getIdleVolStatus())
                        .loadVol(powerSupplyV2Test.getLoadVol())
                        .loadVolStatus(powerSupplyV2Test.getLoadVolStatus())
                        .loadCurrent(powerSupplyV2Test.getLoadCurrent())
                        .loadCurrentStatus(powerSupplyV2Test.getLoadCurrentStatus())
                        .operatingPower(powerSupplyV2Test.getOperatingPower())
                        .noiseLevel(powerSupplyV2Test.getNoiseLevel())
                        .status(powerSupplyV2Test.getStatus())
                        .dateTime(powerSupplyV2Test.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addOpValveTest(OpValveTestAddRequest opValveTestAddRequest) {
        return Mono.just(opValveTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(opValveTestAddRequest.getDeviceMac())
                        .map(device -> toOpValveTest(opValveTestAddRequest, device))
                        .map(opValveTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(opValveTest -> ResponseEntity.ok(CommonResponse.builder().message("Op valve test added successfully").status("SUCCESS").build()));
    }

    private OpValveTestData toOpValveTest(OpValveTestAddRequest opValveTestAddRequest, Device device) {
        return OpValveTestData.builder()
                .device(device)
                .serialNumber(opValveTestAddRequest.getQrCode())
                .physicalInspectionState(opValveTestAddRequest.getPhysicalInspectionState())
                .startOpeningPressure(opValveTestAddRequest.getStartOpeningPressure())
                .startOpeningFlowrate(opValveTestAddRequest.getStartOpeningFlowrate())
                .valveStartOpeningState(opValveTestAddRequest.getValveStartOpeningState())
                .fullyOpeningFlowrate(opValveTestAddRequest.getFullyOpeningFlowrate())
                .fullyOpeningPressure(opValveTestAddRequest.getFullyOpeningPressure())
                .valveFullyOpeningState(opValveTestAddRequest.getValveFullyOpeningState())
                .closingFlowrate(opValveTestAddRequest.getClosingFlowrate())
                .closingPressure(opValveTestAddRequest.getClosingPressure())
                .valveClosingState(opValveTestAddRequest.getValveClosingState())
                .overallOpValveState(opValveTestAddRequest.getOverallOpValveState())
                .status(opValveTestAddRequest.getPhysicalInspectionState()
                        && opValveTestAddRequest.getValveStartOpeningState()
                        && opValveTestAddRequest.getValveFullyOpeningState()
                        && opValveTestAddRequest.getValveClosingState())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<OpValveTestDto>>>> getOpValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting op valve test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return opValveTestRepository.findByCustomQuery(getCustomQuery(OpValveTestData.class, request, userDetails));
                    } else {
                        return opValveTestRepository.findByCustomQuery(getCustomQuery(OpValveTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(opValveTestData -> Mono.just(valveSequenceTestRepository.countByCustomQuery(getCustomCountQuery(OpValveTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<OpValveTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<OpValveTestDto>builder()
                                        .tests(getOpValveDtoFromEntity(opValveTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Op Valve Tests")));
    }

    private List<OpValveTestDto> getOpValveDtoFromEntity(List<OpValveTestData> opValveTestData) {
        return opValveTestData.stream()
                .map(opValveTest -> OpValveTestDto.builder()
                        .testId(opValveTest.getTestId())
                        .deviceId(opValveTest.getDevice().getDeviceId())
                        .serialNumber(opValveTest.getSerialNumber())
                        .physicalInspectionState(opValveTest.getPhysicalInspectionState())
                        .startOpeningPressure(opValveTest.getStartOpeningPressure())
                        .startOpeningFlowrate(opValveTest.getStartOpeningFlowrate())
                        .valveStartOpeningState(opValveTest.getValveStartOpeningState())
                        .fullyOpeningFlowrate(opValveTest.getFullyOpeningFlowrate())
                        .fullyOpeningPressure(opValveTest.getFullyOpeningPressure())
                        .valveFullyOpeningState(opValveTest.getValveFullyOpeningState())
                        .closingFlowrate(opValveTest.getClosingFlowrate())
                        .closingPressure(opValveTest.getClosingPressure())
                        .valveClosingState(opValveTest.getValveClosingState())
                        .overallOpValveState(opValveTest.getOverallOpValveState())
                        .status(opValveTest.getStatus())
                        .dateTime(opValveTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addValveSequenceTest(ValveSequenceTestAddRequest valveSequenceTestAddRequest) {
        return Mono.just(valveSequenceTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(valveSequenceTestAddRequest.getDeviceMac())
                        .map(device -> toValveSequenceTest(valveSequenceTestAddRequest, device))
                        .map(valveSequenceTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(valveSequenceTest -> ResponseEntity.ok(CommonResponse.builder().message("Valve sequence test added successfully").status("SUCCESS").build()));
    }

    private ValveSequenceTestData toValveSequenceTest(ValveSequenceTestAddRequest valveSequenceTestAddRequest, Device device) {
        return ValveSequenceTestData.builder()
                .device(device)
                .serialNumber(valveSequenceTestAddRequest.getQrCode())
                .physicalInspectionState(valveSequenceTestAddRequest.getPhysicalInspectionState())
                .manifoldSealPressure(valveSequenceTestAddRequest.getManifoldSealPressure())
                .manifoldPressureAfter1Sec(valveSequenceTestAddRequest.getManifoldPressureAfter1Sec())
                .manifoldPressureState(valveSequenceTestAddRequest.getManifoldPressureState())
                .valve1InflationPressure(valveSequenceTestAddRequest.getValve1InflationPressure())
                .valve1State(valveSequenceTestAddRequest.getValve1State())
                .valve3InflationPressure(valveSequenceTestAddRequest.getValve3InflationPressure())
                .valve3State(valveSequenceTestAddRequest.getValve3State())
                .valve5InflationPressure(valveSequenceTestAddRequest.getValve5InflationPressure())
                .valve5State(valveSequenceTestAddRequest.getValve5State())
                .valve7InflationPressure(valveSequenceTestAddRequest.getValve7InflationPressure())
                .valve7State(valveSequenceTestAddRequest.getValve7State())
                .valve9InflationPressure(valveSequenceTestAddRequest.getValve9InflationPressure())
                .valve9State(valveSequenceTestAddRequest.getValve9State())
                .valve11InflationPressure(valveSequenceTestAddRequest.getValve11InflationPressure())
                .valve11State(valveSequenceTestAddRequest.getValve11State())
                .valve13InflationPressure(valveSequenceTestAddRequest.getValve13InflationPressure())
                .valve13State(valveSequenceTestAddRequest.getValve13State())
                .valve15InflationPressure(valveSequenceTestAddRequest.getValve15InflationPressure())
                .valve15State(valveSequenceTestAddRequest.getValve15State())
                .valve17InflationPressure(valveSequenceTestAddRequest.getValve17InflationPressure())
                .valve17State(valveSequenceTestAddRequest.getValve17State())
                .valve19InflationPressure(valveSequenceTestAddRequest.getValve19InflationPressure())
                .valve19State(valveSequenceTestAddRequest.getValve19State())
                .valve21InflationPressure(valveSequenceTestAddRequest.getValve21InflationPressure())
                .valve21State(valveSequenceTestAddRequest.getValve21State())
                .valve23InflationPressure(valveSequenceTestAddRequest.getValve23InflationPressure())
                .valve23State(valveSequenceTestAddRequest.getValve23State())
                .valve25InflationPressure(valveSequenceTestAddRequest.getValve25InflationPressure())
                .valve25State(valveSequenceTestAddRequest.getValve25State())
                .valve27InflationPressure(valveSequenceTestAddRequest.getValve27InflationPressure())
                .valve27State(valveSequenceTestAddRequest.getValve27State())
                .valve29InflationPressure(valveSequenceTestAddRequest.getValve29InflationPressure())
                .valve29State(valveSequenceTestAddRequest.getValve29State())
                .valve31InflationPressure(valveSequenceTestAddRequest.getValve31InflationPressure())
                .valve31State(valveSequenceTestAddRequest.getValve31State())
                .valve33InflationPressure(valveSequenceTestAddRequest.getValve33InflationPressure())
                .valve33State(valveSequenceTestAddRequest.getValve33State())
                .valve35InflationPressure(valveSequenceTestAddRequest.getValve35InflationPressure())
                .valve35State(valveSequenceTestAddRequest.getValve35State())
                .valve37InflationPressure(valveSequenceTestAddRequest.getValve37InflationPressure())
                .valve37State(valveSequenceTestAddRequest.getValve37State())
                .valve39InflationPressure(valveSequenceTestAddRequest.getValve39InflationPressure())
                .valve39State(valveSequenceTestAddRequest.getValve39State())
                .valve41InflationPressure(valveSequenceTestAddRequest.getValve41InflationPressure())
                .valve41State(valveSequenceTestAddRequest.getValve41State())
                .valve43InflationPressure(valveSequenceTestAddRequest.getValve43InflationPressure())
                .valve43State(valveSequenceTestAddRequest.getValve43State())
                .valve45InflationPressure(valveSequenceTestAddRequest.getValve45InflationPressure())
                .valve45State(valveSequenceTestAddRequest.getValve45State())
                .valve47InflationPressure(valveSequenceTestAddRequest.getValve47InflationPressure())
                .valve47State(valveSequenceTestAddRequest.getValve47State())
                .valve49InflationPressure(valveSequenceTestAddRequest.getValve49InflationPressure())
                .valve49State(valveSequenceTestAddRequest.getValve49State())
                .valve51InflationPressure(valveSequenceTestAddRequest.getValve51InflationPressure())
                .valve51State(valveSequenceTestAddRequest.getValve51State())
                .valve53InflationPressure(valveSequenceTestAddRequest.getValve53InflationPressure())
                .valve53State(valveSequenceTestAddRequest.getValve53State())
                .valve55InflationPressure(valveSequenceTestAddRequest.getValve55InflationPressure())
                .valve55State(valveSequenceTestAddRequest.getValve55State())
                .valve57InflationPressure(valveSequenceTestAddRequest.getValve57InflationPressure())
                .valve57State(valveSequenceTestAddRequest.getValve57State())
                .valve59InflationPressure(valveSequenceTestAddRequest.getValve59InflationPressure())
                .valve59State(valveSequenceTestAddRequest.getValve59State())
                .valve61InflationPressure(valveSequenceTestAddRequest.getValve61InflationPressure())
                .valve61State(valveSequenceTestAddRequest.getValve61State())
                .valve63InflationPressure(valveSequenceTestAddRequest.getValve63InflationPressure())
                .valve63State(valveSequenceTestAddRequest.getValve63State())
                .valve2DeflationPressure(valveSequenceTestAddRequest.getValve2DeflationPressure())
                .valve2State(valveSequenceTestAddRequest.getValve2State())
                .valve4DeflationPressure(valveSequenceTestAddRequest.getValve4DeflationPressure())
                .valve4State(valveSequenceTestAddRequest.getValve4State())
                .valve6DeflationPressure(valveSequenceTestAddRequest.getValve6DeflationPressure())
                .valve6State(valveSequenceTestAddRequest.getValve6State())
                .valve8DeflationPressure(valveSequenceTestAddRequest.getValve8DeflationPressure())
                .valve8State(valveSequenceTestAddRequest.getValve8State())
                .valve10DeflationPressure(valveSequenceTestAddRequest.getValve10DeflationPressure())
                .valve10State(valveSequenceTestAddRequest.getValve10State())
                .valve12DeflationPressure(valveSequenceTestAddRequest.getValve12DeflationPressure())
                .valve12State(valveSequenceTestAddRequest.getValve12State())
                .valve14DeflationPressure(valveSequenceTestAddRequest.getValve14DeflationPressure())
                .valve14State(valveSequenceTestAddRequest.getValve14State())
                .valve16DeflationPressure(valveSequenceTestAddRequest.getValve16DeflationPressure())
                .valve16State(valveSequenceTestAddRequest.getValve16State())
                .valve18DeflationPressure(valveSequenceTestAddRequest.getValve18DeflationPressure())
                .valve18State(valveSequenceTestAddRequest.getValve18State())
                .valve20DeflationPressure(valveSequenceTestAddRequest.getValve20DeflationPressure())
                .valve20State(valveSequenceTestAddRequest.getValve20State())
                .valve22DeflationPressure(valveSequenceTestAddRequest.getValve22DeflationPressure())
                .valve22State(valveSequenceTestAddRequest.getValve22State())
                .valve24DeflationPressure(valveSequenceTestAddRequest.getValve24DeflationPressure())
                .valve24State(valveSequenceTestAddRequest.getValve24State())
                .valve26DeflationPressure(valveSequenceTestAddRequest.getValve26DeflationPressure())
                .valve26State(valveSequenceTestAddRequest.getValve26State())
                .valve28DeflationPressure(valveSequenceTestAddRequest.getValve28DeflationPressure())
                .valve28State(valveSequenceTestAddRequest.getValve28State())
                .valve30DeflationPressure(valveSequenceTestAddRequest.getValve30DeflationPressure())
                .valve30State(valveSequenceTestAddRequest.getValve30State())
                .valve32DeflationPressure(valveSequenceTestAddRequest.getValve32DeflationPressure())
                .valve32State(valveSequenceTestAddRequest.getValve32State())
                .valve34DeflationPressure(valveSequenceTestAddRequest.getValve34DeflationPressure())
                .valve34State(valveSequenceTestAddRequest.getValve34State())
                .valve36DeflationPressure(valveSequenceTestAddRequest.getValve36DeflationPressure())
                .valve36State(valveSequenceTestAddRequest.getValve36State())
                .valve38DeflationPressure(valveSequenceTestAddRequest.getValve38DeflationPressure())
                .valve38State(valveSequenceTestAddRequest.getValve38State())
                .valve40DeflationPressure(valveSequenceTestAddRequest.getValve40DeflationPressure())
                .valve40State(valveSequenceTestAddRequest.getValve40State())
                .valve42DeflationPressure(valveSequenceTestAddRequest.getValve42DeflationPressure())
                .valve42State(valveSequenceTestAddRequest.getValve42State())
                .valve44DeflationPressure(valveSequenceTestAddRequest.getValve44DeflationPressure())
                .valve44State(valveSequenceTestAddRequest.getValve44State())
                .valve46DeflationPressure(valveSequenceTestAddRequest.getValve46DeflationPressure())
                .valve46State(valveSequenceTestAddRequest.getValve46State())
                .valve48DeflationPressure(valveSequenceTestAddRequest.getValve48DeflationPressure())
                .valve48State(valveSequenceTestAddRequest.getValve48State())
                .valve50DeflationPressure(valveSequenceTestAddRequest.getValve50DeflationPressure())
                .valve50State(valveSequenceTestAddRequest.getValve50State())
                .valve52DeflationPressure(valveSequenceTestAddRequest.getValve52DeflationPressure())
                .valve52State(valveSequenceTestAddRequest.getValve52State())
                .valve54DeflationPressure(valveSequenceTestAddRequest.getValve54DeflationPressure())
                .valve54State(valveSequenceTestAddRequest.getValve54State())
                .valve56DeflationPressure(valveSequenceTestAddRequest.getValve56DeflationPressure())
                .valve56State(valveSequenceTestAddRequest.getValve56State())
                .valve58DeflationPressure(valveSequenceTestAddRequest.getValve58DeflationPressure())
                .valve58State(valveSequenceTestAddRequest.getValve58State())
                .valve60DeflationPressure(valveSequenceTestAddRequest.getValve60DeflationPressure())
                .valve60State(valveSequenceTestAddRequest.getValve60State())
                .valve62DeflationPressure(valveSequenceTestAddRequest.getValve62DeflationPressure())
                .valve62State(valveSequenceTestAddRequest.getValve62State())
                .valve64DeflationPressure(valveSequenceTestAddRequest.getValve64DeflationPressure())
                .valve64State(valveSequenceTestAddRequest.getValve64State())
                .overallValveSequenceState(valveSequenceTestAddRequest.getOverallValveSequenceState())
//                .overallValveSequenceState(valveSequenceTestAddRequest.getPhysicalInspectionState() && valveSequenceTestAddRequest.getManifoldPressureState() && valveSequenceTestAddRequest.getValve1State() && valveSequenceTestAddRequest.getValve3State() && valveSequenceTestAddRequest.getValve5State() && valveSequenceTestAddRequest.getValve7State() && valveSequenceTestAddRequest.getValve9State() && valveSequenceTestAddRequest.getValve11State() && valveSequenceTestAddRequest.getValve13State() && valveSequenceTestAddRequest.getValve15State() && valveSequenceTestAddRequest.getValve17State() && valveSequenceTestAddRequest.getValve19State() && valveSequenceTestAddRequest.getValve21State() && valveSequenceTestAddRequest.getValve23State() && valveSequenceTestAddRequest.getValve25State() && valveSequenceTestAddRequest.getValve27State() && valveSequenceTestAddRequest.getValve29State() && valveSequenceTestAddRequest.getValve31State() && valveSequenceTestAddRequest.getValve33State() && valveSequenceTestAddRequest.getValve35State() && valveSequenceTestAddRequest.getValve37State() && valveSequenceTestAddRequest.getValve39State() && valveSequenceTestAddRequest.getValve41State() && valveSequenceTestAddRequest.getValve43State() && valveSequenceTestAddRequest.getValve45State() && valveSequenceTestAddRequest.getValve47State() && valveSequenceTestAddRequest.getValve49State() && valveSequenceTestAddRequest.getValve51State() && valveSequenceTestAddRequest.getValve53State() && valveSequenceTestAddRequest.getValve55State() && valveSequenceTestAddRequest.getValve57State() && valveSequenceTestAddRequest.getValve59State() && valveSequenceTestAddRequest.getValve61State() && valveSequenceTestAddRequest.getValve63State() && valveSequenceTestAddRequest.getValve2State() && valveSequenceTestAddRequest.getValve4State() && valveSequenceTestAddRequest.getValve6State() && valveSequenceTestAddRequest.getValve8State() && valveSequenceTestAddRequest.getValve10State() && valveSequenceTestAddRequest.getValve12State() && valveSequenceTestAddRequest.getValve14State() && valveSequenceTestAddRequest.getValve16State() && valveSequenceTestAddRequest.getValve18State() && valveSequenceTestAddRequest.getValve20State() && valveSequenceTestAddRequest.getValve22State() && valveSequenceTestAddRequest.getValve24State() && valveSequenceTestAddRequest.getValve26State() && valveSequenceTestAddRequest.getValve28State() && valveSequenceTestAddRequest.getValve30State() && valveSequenceTestAddRequest.getValve32State() && valveSequenceTestAddRequest.getValve34State() && valveSequenceTestAddRequest.getValve36State() && valveSequenceTestAddRequest.getValve38State() && valveSequenceTestAddRequest.getValve40State() && valveSequenceTestAddRequest.getValve42State() && valveSequenceTestAddRequest.getValve44State() && valveSequenceTestAddRequest.getValve46State() && valveSequenceTestAddRequest.getValve48State() && valveSequenceTestAddRequest.getValve50State() && valveSequenceTestAddRequest.getValve52State() && valveSequenceTestAddRequest.getValve54State() && valveSequenceTestAddRequest.getValve56State() && valveSequenceTestAddRequest.getValve58State() && valveSequenceTestAddRequest.getValve60State() && valveSequenceTestAddRequest.getValve62State() && valveSequenceTestAddRequest.getValve64State())
                .status(valveSequenceTestAddRequest.getPhysicalInspectionState()
                        && valveSequenceTestAddRequest.getManifoldPressureState()
                        && valveSequenceTestAddRequest.getValve1State()
                        && valveSequenceTestAddRequest.getValve3State()
                        && valveSequenceTestAddRequest.getValve5State()
                        && valveSequenceTestAddRequest.getValve7State()
                        && valveSequenceTestAddRequest.getValve9State()
                        && valveSequenceTestAddRequest.getValve11State()
                        && valveSequenceTestAddRequest.getValve13State()
                        && valveSequenceTestAddRequest.getValve15State()
                        && valveSequenceTestAddRequest.getValve17State()
                        && valveSequenceTestAddRequest.getValve19State()
                        && valveSequenceTestAddRequest.getValve21State()
                        && valveSequenceTestAddRequest.getValve23State()
                        && valveSequenceTestAddRequest.getValve25State()
                        && valveSequenceTestAddRequest.getValve27State()
                        && valveSequenceTestAddRequest.getValve29State()
                        && valveSequenceTestAddRequest.getValve31State()
                        && valveSequenceTestAddRequest.getValve33State()
                        && valveSequenceTestAddRequest.getValve35State()
                        && valveSequenceTestAddRequest.getValve37State()
                        && valveSequenceTestAddRequest.getValve39State()
                        && valveSequenceTestAddRequest.getValve41State()
                        && valveSequenceTestAddRequest.getValve43State()
                        && valveSequenceTestAddRequest.getValve45State()
                        && valveSequenceTestAddRequest.getValve47State()
                        && valveSequenceTestAddRequest.getValve49State()
                        && valveSequenceTestAddRequest.getValve51State()
                        && valveSequenceTestAddRequest.getValve53State()
                        && valveSequenceTestAddRequest.getValve55State()
                        && valveSequenceTestAddRequest.getValve57State()
                        && valveSequenceTestAddRequest.getValve59State()
                        && valveSequenceTestAddRequest.getValve61State()
                        && valveSequenceTestAddRequest.getValve63State()
                        && valveSequenceTestAddRequest.getValve2State()
                        && valveSequenceTestAddRequest.getValve4State()
                        && valveSequenceTestAddRequest.getValve6State()
                        && valveSequenceTestAddRequest.getValve8State()
                        && valveSequenceTestAddRequest.getValve10State()
                        && valveSequenceTestAddRequest.getValve12State()
                        && valveSequenceTestAddRequest.getValve14State()
                        && valveSequenceTestAddRequest.getValve16State()
                        && valveSequenceTestAddRequest.getValve18State()
                        && valveSequenceTestAddRequest.getValve20State()
                        && valveSequenceTestAddRequest.getValve22State()
                        && valveSequenceTestAddRequest.getValve24State()
                        && valveSequenceTestAddRequest.getValve26State()
                        && valveSequenceTestAddRequest.getValve28State()
                        && valveSequenceTestAddRequest.getValve30State()
                        && valveSequenceTestAddRequest.getValve32State()
                        && valveSequenceTestAddRequest.getValve34State()
                        && valveSequenceTestAddRequest.getValve36State()
                        && valveSequenceTestAddRequest.getValve38State()
                        && valveSequenceTestAddRequest.getValve40State()
                        && valveSequenceTestAddRequest.getValve42State()
                        && valveSequenceTestAddRequest.getValve44State()
                        && valveSequenceTestAddRequest.getValve46State()
                        && valveSequenceTestAddRequest.getValve48State()
                        && valveSequenceTestAddRequest.getValve50State()
                        && valveSequenceTestAddRequest.getValve52State()
                        && valveSequenceTestAddRequest.getValve54State()
                        && valveSequenceTestAddRequest.getValve56State()
                        && valveSequenceTestAddRequest.getValve58State()
                        && valveSequenceTestAddRequest.getValve60State()
                        && valveSequenceTestAddRequest.getValve62State()
                        && valveSequenceTestAddRequest.getValve64State())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveSequenceTestDto>>>> getValveSequenceTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting valve sequence test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return valveSequenceTestRepository.findByCustomQuery(getCustomQuery(ValveSequenceTestData.class, request, userDetails));
                    } else {
                        return valveSequenceTestRepository.findByCustomQuery(getCustomQuery(ValveSequenceTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(valveSequenceTestData -> Mono.just(valveSequenceTestRepository.countByCustomQuery(getCustomCountQuery(ValveSequenceTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<ValveSequenceTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<ValveSequenceTestDto>builder()
                                        .tests(getValveSequenceDtoFromEntity(valveSequenceTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Valve Sequence Tests")));
    }

    private List<ValveSequenceTestDto> getValveSequenceDtoFromEntity(List<ValveSequenceTestData> valveSequenceTestData) {
        return valveSequenceTestData.stream()
                .map(valveSequenceTest -> ValveSequenceTestDto.builder()
                        .testId(valveSequenceTest.getTestId())
                        .deviceId(valveSequenceTest.getDevice().getDeviceId())
                        .serialNumber(valveSequenceTest.getSerialNumber())
                        .physicalInspectionState(valveSequenceTest.getPhysicalInspectionState())
                        .manifoldSealPressure(valveSequenceTest.getManifoldSealPressure())
                        .manifoldPressureAfter1Sec(valveSequenceTest.getManifoldPressureAfter1Sec())
                        .manifoldPressureState(valveSequenceTest.getManifoldPressureState())
                        .valve1InflationPressure(valveSequenceTest.getValve1InflationPressure())
                        .valve1State(valveSequenceTest.getValve1State())
                        .valve3InflationPressure(valveSequenceTest.getValve3InflationPressure())
                        .valve3State(valveSequenceTest.getValve3State())
                        .valve5InflationPressure(valveSequenceTest.getValve5InflationPressure())
                        .valve5State(valveSequenceTest.getValve5State())
                        .valve7InflationPressure(valveSequenceTest.getValve7InflationPressure())
                        .valve7State(valveSequenceTest.getValve7State())
                        .valve9InflationPressure(valveSequenceTest.getValve9InflationPressure())
                        .valve9State(valveSequenceTest.getValve9State())
                        .valve11InflationPressure(valveSequenceTest.getValve11InflationPressure())
                        .valve11State(valveSequenceTest.getValve11State())
                        .valve13InflationPressure(valveSequenceTest.getValve13InflationPressure())
                        .valve13State(valveSequenceTest.getValve13State())
                        .valve15InflationPressure(valveSequenceTest.getValve15InflationPressure())
                        .valve15State(valveSequenceTest.getValve15State())
                        .valve17InflationPressure(valveSequenceTest.getValve17InflationPressure())
                        .valve17State(valveSequenceTest.getValve17State())
                        .valve19InflationPressure(valveSequenceTest.getValve19InflationPressure())
                        .valve19State(valveSequenceTest.getValve19State())
                        .valve21InflationPressure(valveSequenceTest.getValve21InflationPressure())
                        .valve21State(valveSequenceTest.getValve21State())
                        .valve23InflationPressure(valveSequenceTest.getValve23InflationPressure())
                        .valve23State(valveSequenceTest.getValve23State())
                        .valve25InflationPressure(valveSequenceTest.getValve25InflationPressure())
                        .valve25State(valveSequenceTest.getValve25State())
                        .valve27InflationPressure(valveSequenceTest.getValve27InflationPressure())
                        .valve27State(valveSequenceTest.getValve27State())
                        .valve29InflationPressure(valveSequenceTest.getValve29InflationPressure())
                        .valve29State(valveSequenceTest.getValve29State())
                        .valve31InflationPressure(valveSequenceTest.getValve31InflationPressure())
                        .valve31State(valveSequenceTest.getValve31State())
                        .valve33InflationPressure(valveSequenceTest.getValve33InflationPressure())
                        .valve33State(valveSequenceTest.getValve33State())
                        .valve35InflationPressure(valveSequenceTest.getValve35InflationPressure())
                        .valve35State(valveSequenceTest.getValve35State())
                        .valve37InflationPressure(valveSequenceTest.getValve37InflationPressure())
                        .valve37State(valveSequenceTest.getValve37State())
                        .valve39InflationPressure(valveSequenceTest.getValve39InflationPressure())
                        .valve39State(valveSequenceTest.getValve39State())
                        .valve41InflationPressure(valveSequenceTest.getValve41InflationPressure())
                        .valve41State(valveSequenceTest.getValve41State())
                        .valve43InflationPressure(valveSequenceTest.getValve43InflationPressure())
                        .valve43State(valveSequenceTest.getValve43State())
                        .valve45InflationPressure(valveSequenceTest.getValve45InflationPressure())
                        .valve45State(valveSequenceTest.getValve45State())
                        .valve47InflationPressure(valveSequenceTest.getValve47InflationPressure())
                        .valve47State(valveSequenceTest.getValve47State())
                        .valve49InflationPressure(valveSequenceTest.getValve49InflationPressure())
                        .valve49State(valveSequenceTest.getValve49State())
                        .valve51InflationPressure(valveSequenceTest.getValve51InflationPressure())
                        .valve51State(valveSequenceTest.getValve51State())
                        .valve53InflationPressure(valveSequenceTest.getValve53InflationPressure())
                        .valve53State(valveSequenceTest.getValve53State())
                        .valve55InflationPressure(valveSequenceTest.getValve55InflationPressure())
                        .valve55State(valveSequenceTest.getValve55State())
                        .valve57InflationPressure(valveSequenceTest.getValve57InflationPressure())
                        .valve57State(valveSequenceTest.getValve57State())
                        .valve59InflationPressure(valveSequenceTest.getValve59InflationPressure())
                        .valve59State(valveSequenceTest.getValve59State())
                        .valve61InflationPressure(valveSequenceTest.getValve61InflationPressure())
                        .valve61State(valveSequenceTest.getValve61State())
                        .valve63InflationPressure(valveSequenceTest.getValve63InflationPressure())
                        .valve63State(valveSequenceTest.getValve63State())
                        .valve2DeflationPressure(valveSequenceTest.getValve2DeflationPressure())
                        .valve2State(valveSequenceTest.getValve2State())
                        .valve4DeflationPressure(valveSequenceTest.getValve4DeflationPressure())
                        .valve4State(valveSequenceTest.getValve4State())
                        .valve6DeflationPressure(valveSequenceTest.getValve6DeflationPressure())
                        .valve6State(valveSequenceTest.getValve6State())
                        .valve8DeflationPressure(valveSequenceTest.getValve8DeflationPressure())
                        .valve8State(valveSequenceTest.getValve8State())
                        .valve10DeflationPressure(valveSequenceTest.getValve10DeflationPressure())
                        .valve10State(valveSequenceTest.getValve10State())
                        .valve12DeflationPressure(valveSequenceTest.getValve12DeflationPressure())
                        .valve12State(valveSequenceTest.getValve12State())
                        .valve14DeflationPressure(valveSequenceTest.getValve14DeflationPressure())
                        .valve14State(valveSequenceTest.getValve14State())
                        .valve16DeflationPressure(valveSequenceTest.getValve16DeflationPressure())
                        .valve16State(valveSequenceTest.getValve16State())
                        .valve18DeflationPressure(valveSequenceTest.getValve18DeflationPressure())
                        .valve18State(valveSequenceTest.getValve18State())
                        .valve20DeflationPressure(valveSequenceTest.getValve20DeflationPressure())
                        .valve20State(valveSequenceTest.getValve20State())
                        .valve22DeflationPressure(valveSequenceTest.getValve22DeflationPressure())
                        .valve22State(valveSequenceTest.getValve22State())
                        .valve24DeflationPressure(valveSequenceTest.getValve24DeflationPressure())
                        .valve24State(valveSequenceTest.getValve24State())
                        .valve26DeflationPressure(valveSequenceTest.getValve26DeflationPressure())
                        .valve26State(valveSequenceTest.getValve26State())
                        .valve28DeflationPressure(valveSequenceTest.getValve28DeflationPressure())
                        .valve28State(valveSequenceTest.getValve28State())
                        .valve30DeflationPressure(valveSequenceTest.getValve30DeflationPressure())
                        .valve30State(valveSequenceTest.getValve30State())
                        .valve32DeflationPressure(valveSequenceTest.getValve32DeflationPressure())
                        .valve32State(valveSequenceTest.getValve32State())
                        .valve34DeflationPressure(valveSequenceTest.getValve34DeflationPressure())
                        .valve34State(valveSequenceTest.getValve34State())
                        .valve36DeflationPressure(valveSequenceTest.getValve36DeflationPressure())
                        .valve36State(valveSequenceTest.getValve36State())
                        .valve38DeflationPressure(valveSequenceTest.getValve38DeflationPressure())
                        .valve38State(valveSequenceTest.getValve38State())
                        .valve40DeflationPressure(valveSequenceTest.getValve40DeflationPressure())
                        .valve40State(valveSequenceTest.getValve40State())
                        .valve42DeflationPressure(valveSequenceTest.getValve42DeflationPressure())
                        .valve42State(valveSequenceTest.getValve42State())
                        .valve44DeflationPressure(valveSequenceTest.getValve44DeflationPressure())
                        .valve44State(valveSequenceTest.getValve44State())
                        .valve46DeflationPressure(valveSequenceTest.getValve46DeflationPressure())
                        .valve46State(valveSequenceTest.getValve46State())
                        .valve48DeflationPressure(valveSequenceTest.getValve48DeflationPressure())
                        .valve48State(valveSequenceTest.getValve48State())
                        .valve50DeflationPressure(valveSequenceTest.getValve50DeflationPressure())
                        .valve50State(valveSequenceTest.getValve50State())
                        .valve52DeflationPressure(valveSequenceTest.getValve52DeflationPressure())
                        .valve52State(valveSequenceTest.getValve52State())
                        .valve54DeflationPressure(valveSequenceTest.getValve54DeflationPressure())
                        .valve54State(valveSequenceTest.getValve54State())
                        .valve56DeflationPressure(valveSequenceTest.getValve56DeflationPressure())
                        .valve56State(valveSequenceTest.getValve56State())
                        .valve58DeflationPressure(valveSequenceTest.getValve58DeflationPressure())
                        .valve58State(valveSequenceTest.getValve58State())
                        .valve60DeflationPressure(valveSequenceTest.getValve60DeflationPressure())
                        .valve60State(valveSequenceTest.getValve60State())
                        .valve62DeflationPressure(valveSequenceTest.getValve62DeflationPressure())
                        .valve62State(valveSequenceTest.getValve62State())
                        .valve64DeflationPressure(valveSequenceTest.getValve64DeflationPressure())
                        .valve64State(valveSequenceTest.getValve64State())
                        .overallValveSequenceState(valveSequenceTest.getOverallValveSequenceState())
                        .status(valveSequenceTest.getStatus())
                        .dateTime(valveSequenceTest.getDateTime())
                        .build())
                .toList();

    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addValveCardTest(ValveCardTestAddRequest valveCardTestAddRequest) {
        return Mono.just(valveCardTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(valveCardTestAddRequest.getDeviceMac())
                        .map(device -> toValveCardTest(valveCardTestAddRequest, device))
                        .map(valveCardTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(opValveTest -> ResponseEntity.ok(CommonResponse.builder().message("Valve Card test added successfully").status("SUCCESS").build()));
    }

    private ValveCardTestData toValveCardTest(ValveCardTestAddRequest valveCardTestAddRequest, Device device) {
        return ValveCardTestData.builder()
                .device(device)
                .serialNumber(valveCardTestAddRequest.getQrCode())
                .physicalInspectionState(valveCardTestAddRequest.getPhysicalInspectionState())
                .rail(valveCardTestAddRequest.getRail())
                .valve1(valveCardTestAddRequest.getValve1())
                .valve2(valveCardTestAddRequest.getValve2())
                .valve3(valveCardTestAddRequest.getValve3())
                .valve4(valveCardTestAddRequest.getValve4())
                .valve5(valveCardTestAddRequest.getValve5())
                .valve6(valveCardTestAddRequest.getValve6())
                .valve7(valveCardTestAddRequest.getValve7())
                .valve8(valveCardTestAddRequest.getValve8())
                .amperageTest(valveCardTestAddRequest.getAmperageTest())
                .shiftRegisterTest(valveCardTestAddRequest.getShiftRegisterTest())
                .overallValveCardState(valveCardTestAddRequest.getOverallValveCardState())
                .status(valveCardTestAddRequest.getPhysicalInspectionState())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ValveCardTestDto>>>> getValveCardTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting valve card test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return valveCardTestRepository.findByCustomQuery(getCustomQuery(ValveCardTestData.class, request, userDetails));
                    } else {
                        return valveCardTestRepository.findByCustomQuery(getCustomQuery(ValveCardTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(valveCardTestData -> Mono.just(valveCardTestRepository.countByCustomQuery(getCustomCountQuery(ValveCardTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<ValveCardTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<ValveCardTestDto>builder()
                                        .tests(getValveCardDtoFromEntity(valveCardTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Valve Card Tests")));
    }

    private List<ValveCardTestDto> getValveCardDtoFromEntity(List<ValveCardTestData> valveCardTestData) {
        return valveCardTestData.stream()
                .map(valveCardTest -> ValveCardTestDto.builder()
                        .testId(valveCardTest.getTestId())
                        .deviceId(valveCardTest.getDevice().getDeviceId())
                        .serialNumber(valveCardTest.getSerialNumber())
                        .physicalInspectionState(valveCardTest.getPhysicalInspectionState())
                        .rail(valveCardTest.getRail())
                        .valve1(valveCardTest.getValve1())
                        .valve2(valveCardTest.getValve2())
                        .valve3(valveCardTest.getValve3())
                        .valve4(valveCardTest.getValve4())
                        .valve5(valveCardTest.getValve5())
                        .valve6(valveCardTest.getValve6())
                        .valve7(valveCardTest.getValve7())
                        .valve8(valveCardTest.getValve8())
                        .amperageTest(valveCardTest.getAmperageTest())
                        .shiftRegisterTest(valveCardTest.getShiftRegisterTest())
                        .overallValveCardState(valveCardTest.getOverallValveCardState())
                        .status(valveCardTest.getStatus())
                        .dateTime(valveCardTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addManiFoldLeakTest(ManiFoldLeakTestAddRequest maniFoldLeakTestAddRequest) {
        return Mono.just(maniFoldLeakTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(maniFoldLeakTestAddRequest.getDeviceMac())
                        .map(device -> toManiFoldLeakTest(maniFoldLeakTestAddRequest, device))
                        .map(maniFoldLeakTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(opManiFoldLeakTest -> ResponseEntity.ok(CommonResponse.builder().message("Manifold Leak test added successfully").status("SUCCESS").build()));
    }

    private ManiFoldLeakTestData toManiFoldLeakTest(ManiFoldLeakTestAddRequest maniFoldLeakTestAddRequest, Device device) {
        return ManiFoldLeakTestData.builder()
                .device(device)
                .serialNumber(maniFoldLeakTestAddRequest.getQrCode())
                .physicalInspectionState(maniFoldLeakTestAddRequest.getPhysicalInspectionState())
                .leakageFlowrate(maniFoldLeakTestAddRequest.getLeakageFlowrate())
                .manifoldLeakState(maniFoldLeakTestAddRequest.getManifoldLeakState())
                .overallManifoldLeakState(maniFoldLeakTestAddRequest.getOverallManifoldLeakState())
                .status(maniFoldLeakTestAddRequest.getPhysicalInspectionState())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<ManiFoldLeakTestDto>>>> getManiFoldLeakTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting manifold leak test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return maniFoldLeakTestRepository.findByCustomQuery(getCustomQuery(ManiFoldLeakTestData.class, request, userDetails));
                    } else {
                        return maniFoldLeakTestRepository.findByCustomQuery(getCustomQuery(ManiFoldLeakTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(maniFoldLeakTestData -> Mono.just(maniFoldLeakTestRepository.countByCustomQuery(getCustomCountQuery(ManiFoldLeakTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<ManiFoldLeakTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<ManiFoldLeakTestDto>builder()
                                        .tests(getManiFoldLeakDtoFromEntity(maniFoldLeakTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Manifold Leak Tests")));
    }

    private List<ManiFoldLeakTestDto> getManiFoldLeakDtoFromEntity(List<ManiFoldLeakTestData> maniFoldLeakTestData) {
        return maniFoldLeakTestData.stream()
                .map(maniFoldLeakTest -> ManiFoldLeakTestDto.builder()
                        .testId(maniFoldLeakTest.getTestId())
                        .deviceId(maniFoldLeakTest.getDevice().getDeviceId())
                        .serialNumber(maniFoldLeakTest.getSerialNumber())
                        .physicalInspectionState(maniFoldLeakTest.getPhysicalInspectionState())
                        .leakageFlowrate(maniFoldLeakTest.getLeakageFlowrate())
                        .manifoldLeakState(maniFoldLeakTest.getManifoldLeakState())
                        .overallManifoldLeakState(maniFoldLeakTest.getOverallManifoldLeakState())
                        .status(maniFoldLeakTest.getStatus())
                        .dateTime(maniFoldLeakTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addUiPcbTest(UiPcbTestAddRequest uiPcbTestAddRequest) {
        return Mono.just(uiPcbTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(uiPcbTestAddRequest.getDeviceMac())
                        .map(device -> toUiPcbTest(uiPcbTestAddRequest, device))
                        .map(uiPcbTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(opUiPcbTest -> ResponseEntity.ok(CommonResponse.builder().message("UI PCB test added successfully").status("SUCCESS").build()));
    }

    private UiPcbTestData toUiPcbTest(UiPcbTestAddRequest uiPcbTestAddRequest, Device device) {
        return UiPcbTestData.builder()
                .device(device)
                .serialNumber(uiPcbTestAddRequest.getQrCode())
                .physicalInspectionState(uiPcbTestAddRequest.getPhysicalInspectionState())
                .redLedState(uiPcbTestAddRequest.getRedLedState())
                .whiteLedState(uiPcbTestAddRequest.getWhiteLedState())
                .ledRingFadeState(uiPcbTestAddRequest.getLedRingFadeState())
                .ledRingOnState(uiPcbTestAddRequest.getLedRingOnState())
                .overallUiPcbState(uiPcbTestAddRequest.getOverallUiPcbState())
                .status(uiPcbTestAddRequest.getPhysicalInspectionState())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<UiPcbTestDto>>>> getUiPcbTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting UI PCB test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return uiPcbTestRepository.findByCustomQuery(getCustomQuery(UiPcbTestData.class, request, userDetails));
                    } else {
                        return uiPcbTestRepository.findByCustomQuery(getCustomQuery(UiPcbTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(uiPcbTestData -> Mono.just(uiPcbTestRepository.countByCustomQuery(getCustomCountQuery(UiPcbTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<UiPcbTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<UiPcbTestDto>builder()
                                        .tests(getUiPcbDtoFromEntity(uiPcbTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get UI PCB Tests")));
    }

    private List<UiPcbTestDto> getUiPcbDtoFromEntity(List<UiPcbTestData> uiPcbTestData) {
        return uiPcbTestData.stream()
                .map(uiPcbTest -> UiPcbTestDto.builder()
                        .testId(uiPcbTest.getTestId())
                        .deviceId(uiPcbTest.getDevice().getDeviceId())
                        .serialNumber(uiPcbTest.getSerialNumber())
                        .physicalInspectionState(uiPcbTest.getPhysicalInspectionState())
                        .redLedState(uiPcbTest.getRedLedState())
                        .whiteLedState(uiPcbTest.getWhiteLedState())
                        .ledRingFadeState(uiPcbTest.getLedRingFadeState())
                        .ledRingOnState(uiPcbTest.getLedRingOnState())
                        .overallUiPcbState(uiPcbTest.getOverallUiPcbState())
                        .status(uiPcbTest.getStatus())
                        .dateTime(uiPcbTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addCableTest(CableTestAddRequest cableTestAddRequest) {
        return Mono.just(cableTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(cableTestAddRequest.getDeviceMac())
                        .map(device -> toCableTest(cableTestAddRequest, device))
                        .map(cableTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(opCableTest -> ResponseEntity.ok(CommonResponse.builder().message("Cable test added successfully").status("SUCCESS").build()));
    }

    private CableTestData toCableTest(CableTestAddRequest cableTestAddRequest, Device device) {
        return CableTestData.builder()
                .device(device)
                .serialNumber(cableTestAddRequest.getQrCode())
                .cableSelection(cableTestAddRequest.getCableSelection())
                .visualInspection(cableTestAddRequest.getVisualInspection())
                .cable1(cableTestAddRequest.getCable1())
                .cable2(cableTestAddRequest.getCable2())
                .cable3(cableTestAddRequest.getCable3())
                .cable4(cableTestAddRequest.getCable4())
                .cable5(cableTestAddRequest.getCable5())
                .cable6(cableTestAddRequest.getCable6())
                .cable7(cableTestAddRequest.getCable7())
                .cable8(cableTestAddRequest.getCable8())
                .cable9(cableTestAddRequest.getCable9())
                .cable10(cableTestAddRequest.getCable10())
                .overallCableState(cableTestAddRequest.getOverallCableState())
                .status(cableTestAddRequest.getVisualInspection() && cableTestAddRequest.getOverallCableState())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<CableTestDto>>>> getCableTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting cable test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return cableTestRepository.findByCustomQuery(getCustomQuery(CableTestData.class, request, userDetails));
                    } else {
                        return cableTestRepository.findByCustomQuery(getCustomQuery(CableTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(cableTestData -> Mono.just(cableTestRepository.countByCustomQuery(getCustomCountQuery(CableTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<CableTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<CableTestDto>builder()
                                        .tests(getCableDtoFromEntity(cableTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Cable Tests")));
    }

    private List<CableTestDto> getCableDtoFromEntity(List<CableTestData> cableTestData) {
        return cableTestData.stream()
                .map(cableTest -> CableTestDto.builder()
                        .testId(cableTest.getTestId())
                        .deviceId(cableTest.getDevice().getDeviceId())
                        .serialNumber(cableTest.getSerialNumber())
                        .cableSelection(cableTest.getCableSelection())
                        .visualInspection(cableTest.getVisualInspection())
                        .cable1(cableTest.getCable1())
                        .cable2(cableTest.getCable2())
                        .cable3(cableTest.getCable3())
                        .cable4(cableTest.getCable4())
                        .cable5(cableTest.getCable5())
                        .cable6(cableTest.getCable6())
                        .cable7(cableTest.getCable7())
                        .cable8(cableTest.getCable8())
                        .cable9(cableTest.getCable9())
                        .cable10(cableTest.getCable10())
                        .overallCableState(cableTest.getOverallCableState())
                        .status(cableTest.getStatus())
                        .dateTime(cableTest.getDateTime())
                        .build())
                .toList();
    }

    @Override
    public Mono<ResponseEntity<CommonResponse>> addFanTest(FanTestAddRequest fanTestAddRequest) {
        return Mono.just(fanTestAddRequest)
                .flatMap(request -> deviceService.getDeviceByMac(fanTestAddRequest.getDeviceMac())
                        .map(device -> toFanTest(fanTestAddRequest, device))
                        .map(fanTestRepository::save))
                .switchIfEmpty(Mono.error(new RuntimeException("Device not found")))
                .map(opFanTest -> ResponseEntity.ok(CommonResponse.builder().message("Fan test added successfully").status("SUCCESS").build()));
    }

    private FanTestData toFanTest(FanTestAddRequest fanTestAddRequest, Device device) {
        return FanTestData.builder()
                .device(device)
                .serialNumber(fanTestAddRequest.getQrCode())
                .visualInspection(fanTestAddRequest.getVisualInspection())
                .drawCurrent(fanTestAddRequest.getDrawCurrent())
                .drawCurrentState(fanTestAddRequest.getDrawCurrentState())
                .fanSpeed(fanTestAddRequest.getFanSpeed())
                .fanSpeedState(fanTestAddRequest.getFanSpeedState())
                .overallFanState(fanTestAddRequest.getOverallFanState())
                .status(fanTestAddRequest.getVisualInspection() && fanTestAddRequest.getOverallFanState())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetTestResponse<FanTestDto>>>> getFanTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting fan test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return fanTestRepository.findByCustomQuery(getCustomQuery(FanTestData.class, request, userDetails));
                    } else {
                        return fanTestRepository.findByCustomQuery(getCustomQuery(FanTestData.class, request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(fanTestData -> Mono.just(fanTestRepository.countByCustomQuery(getCustomCountQuery(FanTestData.class, request, userDetails)))
                        .map(totalRecords -> ApiResponse.<GetTestResponse<FanTestDto>>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetTestResponse.<FanTestDto>builder()
                                        .tests(getFanDtoFromEntity(fanTestData))
                                        .totalRecords(totalRecords)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Fan Tests")));
    }

    private List<FanTestDto> getFanDtoFromEntity(List<FanTestData> fanTestData) {
        return fanTestData.stream()
                .map(fanTest -> FanTestDto.builder()
                        .testId(fanTest.getTestId())
                        .deviceId(fanTest.getDevice().getDeviceId())
                        .serialNumber(fanTest.getSerialNumber())
                        .visualInspection(fanTest.getVisualInspection())
                        .drawCurrent(fanTest.getDrawCurrent())
                        .drawCurrentState(fanTest.getDrawCurrentState())
                        .fanSpeed(fanTest.getFanSpeed())
                        .fanSpeedState(fanTest.getFanSpeedState())
                        .overallFanState(fanTest.getOverallFanState())
                        .status(fanTest.getStatus())
                        .dateTime(fanTest.getDateTime())
                        .build())
                .toList();
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
                case "AIR_PUMP", "POWER_PCB", "POWER_SUPPLY", "VALVE", "AIR_PUMP_V2", "POWER_PCB_V2",
                     "POWER_SUPPLY_V2", "OP_VALVE", "VALVE_SEQUENCE", "VALVE_CARD", "MANI_FOLD_LEAK", "UI_PCB", "CABLE",
                     "FAN" -> filterParts.add("v.status = " + status);
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


    private <T> TypedQuery<Long> getCustomCountQuery(Class<T> entityClass, GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(v) FROM " + entityClass.getSimpleName() + " v JOIN v.device d");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private <T> TypedQuery<T> getCustomQuery(Class<T> entityClass, GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM " + entityClass.getSimpleName() + " v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();
        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<T> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), entityClass);

        return exchangeDateFilterInQuery(query, request);
    }

}
