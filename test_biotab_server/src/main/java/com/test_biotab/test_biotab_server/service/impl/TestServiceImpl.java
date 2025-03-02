package com.test_biotab.test_biotab_server.service.impl;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.AirPumpTestDto;
import com.test_biotab.test_biotab_server.dto.PowerSupplyTestDto;
import com.test_biotab.test_biotab_server.dto.ValveTestDto;
import com.test_biotab.test_biotab_server.entity.AirPumpTestData;
import com.test_biotab.test_biotab_server.entity.Device;
import com.test_biotab.test_biotab_server.entity.PowerSupplyTestData;
import com.test_biotab.test_biotab_server.entity.ValveTestData;
import com.test_biotab.test_biotab_server.repository.AirPumpTestRepository;
import com.test_biotab.test_biotab_server.repository.PowerSupplyTestRepository;
import com.test_biotab.test_biotab_server.repository.ValveTestRepository;
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
    public Mono<ResponseEntity<ApiResponse<GetPowerSupplyTestResponse>>> getPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting power supply test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return powerSupplyTestRepository.findByCustomQuery(getCustomQueryPowerSupplyTest(request, userDetails));
                    } else {
                        assert pageNo != null;
                        return powerSupplyTestRepository.findByCustomQuery(getCustomQueryPowerSupplyTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(powerSupplyTestData -> {
                    long total = powerSupplyTestData.size();
                    long totalFailed = powerSupplyTestData.stream()
                            .filter(data -> !data.getIdleVolStatus() || !data.getLoadVolStatus() || !data.getLoadCurrentStatus())
                            .count();
                    return Mono.just(ApiResponse.<GetPowerSupplyTestResponse>builder()
                            .status("S1000")
                            .statusDescription("Request successful")
                            .data(GetPowerSupplyTestResponse.builder()
                                    .powerSupplyTests(getPowerSupplyDtoFromEntity(powerSupplyTestData))
                                    .totalRecords(total)
                                    .totalFailed(totalFailed)
                                    .build())
                            .build());
                })
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

    private TypedQuery<PowerSupplyTestData> getCustomQueryPowerSupplyTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM PowerSupplyTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<PowerSupplyTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).append(" ORDER BY v.dateTime DESC").toString(), PowerSupplyTestData.class);

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
    public Mono<ResponseEntity<ApiResponse<GetValveTestResponse>>> getValveTest(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting valve test with pattern: {} by user: {}", req.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return valveTestRepository.findByCustomQuery(getCustomQueryValveTest(request, userDetails));
                    } else {
                        assert pageNo != null;
                        return valveTestRepository.findByCustomQuery(getCustomQueryValveTest(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(valveTestData -> {
                    long total = valveTestData.size();
                    long totalFailed = valveTestData.stream()
                            .filter(data -> !data.getIdleVoltageStatus() || !data.getIdleCurrentStatus() || !data.getFlowRateStatus())
                            .count();
                    return Mono.just(ApiResponse.<GetValveTestResponse>builder()
                            .status("S1000")
                            .statusDescription("Request successful")
                            .data(GetValveTestResponse.builder()
                                    .valveTests(getValveDtoFromEntity(valveTestData))
                                    .totalRecords(total)
                                    .totalFailed(totalFailed)
                                    .build())
                            .build());
                })
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("Error getting valve tests", e);
                    return Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get valve Tests"));
                });
    }

    private TypedQuery<ValveTestData> getCustomQueryValveTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM ValveTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<ValveTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), ValveTestData.class);

        return exchangeDateFilterInQuery(query, request);
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
                .flatMap(airPumpTestData -> {
                    long total = airPumpTestData.size();
                    long totalFailed = airPumpTestData.stream()
                            .filter(data -> !data.getIdleVoltageStatus() || !data.getIdleCurrentStatus() || !data.getLoadVoltageStatus() || !data.getLoadCurrentStatus() || !data.getNoiseLevelStatus())
                            .count();
                    return Mono.just(ApiResponse.<GetAirPumpTestResponse>builder()
                            .status("S1000")
                            .statusDescription("Request successful")
                            .data(GetAirPumpTestResponse.builder()
                                    .airPumpTests(getAirPumpDtoFromEntity(airPumpTestData))
                                    .totalRecords(total)
                                    .totalFailed(totalFailed)
                                    .build())
                            .build());
                })
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Air Pump Tests")));

    }

    private TypedQuery<AirPumpTestData> getCustomQueryAirPumpTest(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT v FROM AirPumpTestData v JOIN v.device d");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<AirPumpTestData> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY v.dateTime DESC").toString(), AirPumpTestData.class);

        return exchangeDateFilterInQuery(query, request);
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
