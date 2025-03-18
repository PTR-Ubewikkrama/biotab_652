package com.test_biotab.test_biotab_server.service.impl;

import com.test_biotab.test_biotab_server.domain.ApiResponse;
import com.test_biotab.test_biotab_server.domain.DashBoardSummaryResponse;
import com.test_biotab.test_biotab_server.repository.*;
import com.test_biotab.test_biotab_server.service.DashboardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final ValveTestRepository valveTestRepository;
    private final PowerSupplyTestRepository powerSupplyTestRepository;
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
    private final BTDeviceRepository BTDeviceRepository;
    private final FinalAssemblyRepository finalAssemblyRepository;
    private final DisplayTestRepository displayTestRepository;
    private final MainPCBTestRepository mainPCBTestRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<ResponseEntity<ApiResponse<DashBoardSummaryResponse>>> getSummary(UserDetails userDetails) {
        return Mono.zip(
                Arrays.asList(
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
                                Mono.fromSupplier(() -> finalAssemblyRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(f) FROM FinalAssembly f",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> BTDeviceRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(h) FROM BTDevice h",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> powerPCBTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerPCBTestData p WHERE p.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> powerPCBTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerPCBTestData p WHERE p.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> airPumpV2TestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(a) FROM AirPumpV2TestData a WHERE a.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> airPumpV2TestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(a) FROM AirPumpV2TestData a WHERE a.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> powerPCBV2TestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerPCBV2TestData p WHERE p.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> powerPCBV2TestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerPCBV2TestData p WHERE p.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> powerSupplyV2TestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerSupplyV2TestData p WHERE p.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> powerSupplyV2TestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM PowerSupplyV2TestData p WHERE p.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> opValveTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM OpValveTestData p WHERE p.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> opValveTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(p) FROM OpValveTestData p WHERE p.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> valveSequenceTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(v) FROM ValveSequenceTestData v WHERE v.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> valveSequenceTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(v) FROM ValveSequenceTestData v WHERE v.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> valveCardTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(v) FROM ValveCardTestData v WHERE v.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> valveCardTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(v) FROM ValveCardTestData v WHERE v.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> maniFoldLeakTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(m) FROM ManiFoldLeakTestData m WHERE m.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> maniFoldLeakTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(m) FROM ManiFoldLeakTestData m WHERE m.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> uiPcbTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(u) FROM UiPcbTestData u WHERE u.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> uiPcbTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(u) FROM UiPcbTestData u WHERE u.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> cableTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(c) FROM CableTestData c WHERE c.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> cableTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(c) FROM CableTestData c WHERE c.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> fanTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(f) FROM FanTestData f WHERE f.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> fanTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(f) FROM FanTestData f WHERE f.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> displayTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(d) FROM DisplayTestData d WHERE d.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> displayTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(d) FROM DisplayTestData d WHERE d.status = false",
                                        Long.class
                                )))
                        ),
                        Mono.zip(
                                Mono.fromSupplier(() -> mainPCBTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(m) FROM MainPCBTestData m WHERE m.status = true",
                                        Long.class
                                ))),
                                Mono.fromSupplier(() -> mainPCBTestRepository.countByCustomQuery(entityManager.createQuery(
                                        "SELECT COUNT(m) FROM MainPCBTestData m WHERE m.status = false",
                                        Long.class
                                )))
                        )
                ),
                objects -> {
                    Tuple2<Long, Long> valueTestCounts = (Tuple2<Long, Long>) objects[0];
                    Tuple2<Long, Long> powerSupplyTestCounts = (Tuple2<Long, Long>) objects[1];
                    Tuple2<Long, Long> airPumpTestCounts = (Tuple2<Long, Long>) objects[2];
                    Tuple2<Long, Long> t8Counts = (Tuple2<Long, Long>) objects[3];
                    Tuple2<Long, Long> powerPCBTestCounts = (Tuple2<Long, Long>) objects[4];
                    Tuple2<Long, Long> airPumpV2TestCounts = (Tuple2<Long, Long>) objects[5];
                    Tuple2<Long, Long> powerPCBV2TestCounts = (Tuple2<Long, Long>) objects[6];
                    Tuple2<Long, Long> powerSupplyV2TestCounts = (Tuple2<Long, Long>) objects[7];
                    Tuple2<Long, Long> opValveTestCounts = (Tuple2<Long, Long>) objects[8];
                    Tuple2<Long, Long> valveSequenceTestCounts = (Tuple2<Long, Long>) objects[9];
                    Tuple2<Long, Long> valveCardTestCounts = (Tuple2<Long, Long>) objects[10];
                    Tuple2<Long, Long> maniFoldLeakTestCounts = (Tuple2<Long, Long>) objects[11];
                    Tuple2<Long, Long> uiPcbTestCounts = (Tuple2<Long, Long>) objects[12];
                    Tuple2<Long, Long> cableTestCounts = (Tuple2<Long, Long>) objects[13];
                    Tuple2<Long, Long> fanTestCounts = (Tuple2<Long, Long>) objects[14];
                    Tuple2<Long, Long> displayTestCounts = (Tuple2<Long, Long>) objects[15];
                    Tuple2<Long, Long> mainPCBTestCounts = (Tuple2<Long, Long>) objects[16];

                    Long totalSuccessValueTest = valueTestCounts.getT1();
                    Long totalFailedValueTest = valueTestCounts.getT2();

                    Long totalSuccessPowerSupplyTest = powerSupplyTestCounts.getT1();
                    Long totalFailedPowerSupplyTest = powerSupplyTestCounts.getT2();

                    Long totalSuccessAirPumpTest = airPumpTestCounts.getT1();
                    Long totalFailedAirPumpTest = airPumpTestCounts.getT2();

                    Long totalFinalAssembly = t8Counts.getT1();
                    Long totalHHDevice = t8Counts.getT2();

                    Long totalSuccessPowerPCBTest = powerPCBTestCounts.getT1();
                    Long totalFailedPowerPCBTest = powerPCBTestCounts.getT2();

                    Long totalSuccessAirPumpV2Test = airPumpV2TestCounts.getT1();
                    Long totalFailedAirPumpV2Test = airPumpV2TestCounts.getT2();

                    Long totalSuccessPowerPCBV2Test = powerPCBV2TestCounts.getT1();
                    Long totalFailedPowerPCBV2Test = powerPCBV2TestCounts.getT2();

                    Long totalSuccessPowerSupplyV2Test = powerSupplyV2TestCounts.getT1();
                    Long totalFailedPowerSupplyV2Test = powerSupplyV2TestCounts.getT2();

                    Long totalSuccessOpValveTest = opValveTestCounts.getT1();
                    Long totalFailedOpValveTest = opValveTestCounts.getT2();

                    Long totalSuccessValveSequenceTest = valveSequenceTestCounts.getT1();
                    Long totalFailedValveSequenceTest = valveSequenceTestCounts.getT2();

                    Long totalSuccessValveCardTest = valveCardTestCounts.getT1();
                    Long totalFailedValveCardTest = valveCardTestCounts.getT2();

                    Long totalSuccessManiFoldLeakTest = maniFoldLeakTestCounts.getT1();
                    Long totalFailedManiFoldLeakTest = maniFoldLeakTestCounts.getT2();

                    Long totalSuccessUiPcbTest = uiPcbTestCounts.getT1();
                    Long totalFailedUiPcbTest = uiPcbTestCounts.getT2();

                    Long totalSuccessCableTest = cableTestCounts.getT1();
                    Long totalFailedCableTest = cableTestCounts.getT2();

                    Long totalSuccessFanTest = fanTestCounts.getT1();
                    Long totalFailedFanTest = fanTestCounts.getT2();

                    Long totalSuccessDisplayTest = displayTestCounts.getT1();
                    Long totalFailedDisplayTest = displayTestCounts.getT2();

                    Long totalSuccessMainPcbTest = mainPCBTestCounts.getT1();
                    Long totalFailedMainPcbTest = mainPCBTestCounts.getT2();

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
                                            .totalSuccessPcbTest(totalSuccessPowerPCBTest)
                                            .totalFailedPcbTest(totalFailedPowerPCBTest)
                                            .totalSuccessAirPumpV2Test(totalSuccessAirPumpV2Test)
                                            .totalFailedAirPumpV2Test(totalFailedAirPumpV2Test)
                                            .totalFailedPowerPcbV2Test(totalFailedPowerPCBV2Test)
                                            .totalSuccessPowerPcbV2Test(totalSuccessPowerPCBV2Test)
                                            .totalSuccessPowerSupplyV2Test(totalSuccessPowerSupplyV2Test)
                                            .totalFailedPowerSupplyV2Test(totalFailedPowerSupplyV2Test)
                                            .totalFailedOpValveTest(totalFailedOpValveTest)
                                            .totalSuccessOpValveTest(totalSuccessOpValveTest)
                                            .totalFailedValveSequenceTest(totalFailedValveSequenceTest)
                                            .totalSuccessValveSequenceTest(totalSuccessValveSequenceTest)
                                            .totalFailedValveCardTest(totalFailedValveCardTest)
                                            .totalSuccessValveCardTest(totalSuccessValveCardTest)
                                            .totalFailedManiFoldLeakTest(totalFailedManiFoldLeakTest)
                                            .totalSuccessManiFoldLeakTest(totalSuccessManiFoldLeakTest)
                                            .totalFailedUiPcbTest(totalFailedUiPcbTest)
                                            .totalSuccessUiPcbTest(totalSuccessUiPcbTest)
                                            .totalFailedCableTest(totalFailedCableTest)
                                            .totalSuccessCableTest(totalSuccessCableTest)
                                            .totalFailedFanTest(totalFailedFanTest)
                                            .totalSuccessFanTest(totalSuccessFanTest)
                                            .totalFinalAssembly(totalFinalAssembly)
                                            .totalHHDevice(totalHHDevice)
                                            .totalFailedDisplayTest(totalFailedDisplayTest)
                                            .totalSuccessDisplayTest(totalSuccessDisplayTest)
                                            .totalFailedMainPcbTest(totalFailedMainPcbTest)
                                            .totalSuccessMainPcbTest(totalSuccessMainPcbTest)
                                            .build()
                                    )
                                    .build()
                    );
                }
        ).onErrorResume(
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
