package com.test_jig.test_jig_server.service.impl;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.domain.DashBoardSummaryResponse;
import com.test_jig.test_jig_server.repository.*;
import com.test_jig.test_jig_server.service.DashboardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final ValveTestRepository valveTestRepository;
    private final PowerSupplyTestRepository powerSupplyTestRepository;
    private final AirPumpTestRepository airPumpTestRepository;
    private final BatteryTestRepository batteryTestRepository;
    private final OverPressureValveTestRepository overPressureValveTestRepository;
    private final PcbTestRepository pcbTestRepository;
    private final LatchButtonTestRepository latchButtonTestRepository;
    private final HHDeviceRepository hhDeviceRepository;
    private final FinalAssemblyRepository finalAssemblyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<ResponseEntity<ApiResponse<DashBoardSummaryResponse>>> getSummary(UserDetails userDetails) {
        return Mono.zip(
                        Mono.zip(
                                Mono.fromSupplier(() -> valveTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(v) FROM ValveTestData v WHERE v.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> valveTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(v) FROM ValveTestData v WHERE v.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> powerSupplyTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerSupplyTestData p WHERE p.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> powerSupplyTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerSupplyTestData p WHERE p.status = false",
                                        Long.class
                                )))
                        ),

                        Mono.zip(
                                Mono.fromSupplier(() -> airPumpTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(a) FROM AirPumpTestData a WHERE a.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> airPumpTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(a) FROM AirPumpTestData a WHERE a.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> batteryTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(b) FROM BatteryTestData b WHERE b.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> batteryTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(b) FROM BatteryTestData b WHERE b.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> overPressureValveTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(o) FROM OverPressureValveTestData o WHERE o.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> overPressureValveTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(o) FROM OverPressureValveTestData o WHERE o.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> pcbTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PcbTestData p WHERE p.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> pcbTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PcbTestData p WHERE p.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> latchButtonTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(l) FROM LatchButtonTestData l WHERE l.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> latchButtonTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(l) FROM LatchButtonTestData l WHERE l.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> finalAssemblyRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(f) FROM FinalAssembly f",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> hhDeviceRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(h) FROM HHDevice h",
                                        Long.class
                                )))
                        )
                ).map(results -> {
                    Tuple2<Long, Long> valueTestCounts = results.getT1();
                    Tuple2<Long, Long> powerSupplyTestCounts = results.getT2();
                    Tuple2<Long, Long> airPumpTestCounts = results.getT3();
                    Tuple2<Long, Long> batteryTestCounts = results.getT4();
                    Tuple2<Long, Long> overPressureValveTestCounts = results.getT5();
                    Tuple2<Long, Long> pcbTestCounts = results.getT6();
                    Tuple2<Long, Long> latchButtonTestCounts = results.getT7();
                    Tuple2<Long, Long> t8Counts = results.getT8();

                    Long totalSuccessValueTest = valueTestCounts.getT1();
                    Long totalFailedValueTest = valueTestCounts.getT2();

                    Long totalSuccessPowerSupplyTest = powerSupplyTestCounts.getT1();
                    Long totalFailedPowerSupplyTest = powerSupplyTestCounts.getT2();

                    Long totalSuccessAirPumpTest = airPumpTestCounts.getT1();
                    Long totalFailedAirPumpTest = airPumpTestCounts.getT2();

                    Long totalSuccessBatteryTest = batteryTestCounts.getT1();
                    Long totalFailedBatteryTest = batteryTestCounts.getT2();

                    Long totalSuccessOverPressureTest = overPressureValveTestCounts.getT1();
                    Long totalFailedOverPressureTest = overPressureValveTestCounts.getT2();

                    Long totalSuccessPcbTest = pcbTestCounts.getT1();
                    Long totalFailedPcbTest = pcbTestCounts.getT2();

                    Long totalSuccessLatchButtonTest = latchButtonTestCounts.getT1();
                    Long totalFailedLatchButtonTest = latchButtonTestCounts.getT2();

                    Long totalFinalAssembly = t8Counts.getT1();
                    Long totalHHDevice = t8Counts.getT2();

                    return ResponseEntity.ok(
                            ApiResponse.<DashBoardSummaryResponse>builder()
                                    .status("S1000")
                                    .statusDescription("Success")
                                    .data(DashBoardSummaryResponse.builder()
                                            .totalSuccessValueTest(totalSuccessValueTest)
                                            .totalFailedValueTest(totalFailedValueTest)
                                            .totalSuccessPowerSupplyTest(totalSuccessPowerSupplyTest)
                                            .totalFailedPowerSupplyTest(totalFailedPowerSupplyTest)
                                            .totalSuccessAirPumpTest(totalSuccessAirPumpTest)
                                            .totalFailedAirPumpTest(totalFailedAirPumpTest)
                                            .totalSuccessBatteryTest(totalSuccessBatteryTest)
                                            .totalFailedBatteryTest(totalFailedBatteryTest)
                                            .totalSuccessOverPressureTest(totalSuccessOverPressureTest)
                                            .totalFailedOverPressureTest(totalFailedOverPressureTest)
                                            .totalSuccessPcbTest(totalSuccessPcbTest)
                                            .totalFailedPcbTest(totalFailedPcbTest)
                                            .totalSuccessLatchButtonTest(totalSuccessLatchButtonTest)
                                            .totalFailedLatchButtonTest(totalFailedLatchButtonTest)
                                            .totalFinalAssembly(totalFinalAssembly)
                                            .totalHHDevice(totalHHDevice)
                                            .build()
                                    )
                                    .build()
                    );
                })
                .onErrorResume(
                        throwable -> {
                            log.error("Error occurred while fetching dashboard summary", throwable);
                            return Mono.just(ResponseEntity.status(500).body(ApiResponse.<DashBoardSummaryResponse>builder()
                                    .status("E2005")
                                    .statusDescription("Error occurred while fetching dashboard summary")
                                    .build()));
                        }
                );
    }


}
