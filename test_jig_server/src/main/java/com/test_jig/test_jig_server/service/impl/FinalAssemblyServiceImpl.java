package com.test_jig.test_jig_server.service.impl;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.domain.GetByPatternRequest;
import com.test_jig.test_jig_server.domain.ValidateRequest;
import com.test_jig.test_jig_server.domain.fa.*;
import com.test_jig.test_jig_server.dto.DeviceHHDto;
import com.test_jig.test_jig_server.dto.fa.CartoonBoxDto;
import com.test_jig.test_jig_server.dto.fa.FinalAssemblyDto;
import com.test_jig.test_jig_server.entity.CartoonBox;
import com.test_jig.test_jig_server.entity.FinalAssembly;
import com.test_jig.test_jig_server.entity.HHDevice;
import com.test_jig.test_jig_server.repository.CartoonBoxRepository;
import com.test_jig.test_jig_server.repository.FinalAssemblyRepository;
import com.test_jig.test_jig_server.repository.HHDeviceRepository;
import com.test_jig.test_jig_server.service.FinalAssemblyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinalAssemblyServiceImpl implements FinalAssemblyService {
    private final FinalAssemblyRepository finalAssemblyRepository;
    private final CartoonBoxRepository cartoonBoxRepository;
    private final HHDeviceRepository hhDeviceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateDeviceId(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Device ID: {}", validateRequest.getCode());
                    HHDevice device = hhDeviceRepository.findByCode(validateRequest.getCode());
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(validateRequest.getCode());

                    if (device == null && finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E2225")
                                .statusDescription("Device not found in the system")
                                .build());
                    }
                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E2225")
                                .statusDescription("Device not validated")
                                .build());
                    }
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("Device not added to FA")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E2222")
                            .statusDescription("Device validated")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating device")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<FinalAssemblyDto>>> getFinalAssemblyById(String deviceCode, UserDetails userDetails) {
        return Mono.just(deviceCode)
                .map(deviceId1 -> {
                    log.info("Getting Final Assembly by Device Code: {}", deviceId1);
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(deviceId1);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<FinalAssemblyDto>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<FinalAssemblyDto>builder()
                            .status("S1000")
                            .statusDescription("Final Assembly found")
                            .data(FinalAssemblyDto.builder()
                                    .deviceCode(finalAssembly.getDeviceCode())
                                    .category(finalAssembly.getCategory())
                                    .bladderCode(finalAssembly.getBladderCode())
                                    .uplNumber(finalAssembly.getUplNumber())
                                    .udiNumber(finalAssembly.getUdiNumber())
                                    .adapterCode(finalAssembly.getAdapterCode())
                                    .cartoonNumber(finalAssembly.getCartoonNumber())
                                    .createdAt(finalAssembly.getCreatedAt().toString())
                                    .updatedAt(finalAssembly.getUpdatedAt().toString())
                                    .updatedBy(finalAssembly.getUpdatedBy())
                                    .createdBy(finalAssembly.getCreatedBy())
                                    .build())
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<FinalAssemblyDto>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while getting final assembly")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> addFAStepOne(FAStepOneAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faStepOneAddRequest -> {
                    log.info("Adding FA Step One: {}", faStepOneAddRequest.getDeviceCode());
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(faStepOneAddRequest.getDeviceCode());
                    if (finalAssembly != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly Already found in the system")
                                .build());
                    }
                    finalAssembly = new FinalAssembly();
                    finalAssembly.setCategory(faStepOneAddRequest.getCategory());
                    finalAssembly.setDeviceCode(faStepOneAddRequest.getDeviceCode());
                    finalAssembly.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssembly.setCreatedBy(userDetails.getUsername());
                    finalAssembly.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssemblyRepository.save(finalAssembly);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Step One added")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding FA Step One")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFAStepOne(FAStepOneUpdateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faStepOneUpdateRequest -> {
                    log.info("Updating FA Step One: {}", faStepOneUpdateRequest.getDeviceCode());
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(faStepOneUpdateRequest.getDeviceCode());
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }
                    if (faStepOneUpdateRequest.getCategory() != null) {
                        finalAssembly.setCategory(faStepOneUpdateRequest.getCategory());
                    }
                    if (faStepOneUpdateRequest.getDeviceCode() != null) {
                        finalAssembly.setDeviceCode(faStepOneUpdateRequest.getDeviceCode());
                    }
                    finalAssembly.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssembly.setUpdatedBy(userDetails.getUsername());
                    finalAssemblyRepository.save(finalAssembly);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Step One updated")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while updating FA Step One")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateBladder(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Bladder: {}", validateRequest.getCode());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.bladderCode = :id", FinalAssembly.class);
                    query.setParameter("id", validateRequest.getCode());

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);

                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("Bladder not found in the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E2222")
                            .statusDescription("Bladder Already added to Final Assembly")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating bladder")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateUplNumber(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating UPL Number: {}", validateRequest.getCode());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE REPLACE(REPLACE(d.uplNumber, '\t', ''), ' ', '') = :id", FinalAssembly.class);
                    String normalizedCode = validateRequest.getCode().replaceAll("\\s+", "").trim();
                    query.setParameter("id", normalizedCode);

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("UPL Number not found in the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E2222")
                            .statusDescription("UPL Number Already added to Final Assembly")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating UPL Number")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> addFAStepTwo(FAStepTwoAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faStepTwoAddRequest -> {
                    log.info("Adding FA Step Two: {}", faStepTwoAddRequest.getDeviceCode());
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(faStepTwoAddRequest.getDeviceCode());
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }
                    if (request.getBladderCode() != null) {
                        finalAssembly.setBladderCode(faStepTwoAddRequest.getBladderCode());
                    }
                    if (request.getUplNumber() != null) {
                        finalAssembly.setUplNumber(faStepTwoAddRequest.getUplNumber());
                    }
                    finalAssembly.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssembly.setUpdatedBy(userDetails.getUsername());
                    finalAssemblyRepository.save(finalAssembly);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Step Two added")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding FA Step Two")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFAStepTwo(FAStepTwoUpdateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faStepTwoUpdateRequest -> {
                    log.info("Updating FA Step Two: {}", faStepTwoUpdateRequest.getDeviceCode());
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(faStepTwoUpdateRequest.getDeviceCode());
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }
                    if (faStepTwoUpdateRequest.getBladderCode() != null) {
                        finalAssembly.setBladderCode(faStepTwoUpdateRequest.getBladderCode());
                    }
                    if (faStepTwoUpdateRequest.getUplNumber() != null) {
                        finalAssembly.setUplNumber(faStepTwoUpdateRequest.getUplNumber());
                    }
                    finalAssembly.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssembly.setUpdatedBy(userDetails.getUsername());
                    finalAssemblyRepository.save(finalAssembly);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Step Two updated")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while updating FA Step Two")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateUdi(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating UDI: {}", validateRequest.getCode());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.udiNumber = :id", FinalAssembly.class);
                    query.setParameter("id", validateRequest.getCode());

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("UDI not found in the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E2222")
                            .statusDescription("UDI Already added to Final Assembly")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating UDI")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateAdapter(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Adapter: {}", validateRequest.getCode());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.adapterCode = :id", FinalAssembly.class);
                    query.setParameter("id", validateRequest.getCode());

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("Adapter not found in the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E2222")
                            .statusDescription("Adapter Already added to Final Assembly")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating adapter")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> addFAStepThree(FAStepThreeAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faStepThreeAddRequest -> {
                    log.info("Adding FA Step Three: {}", faStepThreeAddRequest.getUplNumber());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE REPLACE(REPLACE(d.uplNumber, '\t', ''), ' ', '') = :id", FinalAssembly.class);
                    String normalizedCode = faStepThreeAddRequest.getUplNumber().replaceAll("\\s+", "").trim();
                    query.setParameter("id", normalizedCode);

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }
                    if (request.getUdiNumber() != null) {
                        finalAssembly.setUdiNumber(faStepThreeAddRequest.getUdiNumber());
                    }
                    if (request.getAdapterCode() != null) {
                        finalAssembly.setAdapterCode(faStepThreeAddRequest.getAdapterCode());
                    }
                    finalAssembly.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssembly.setUpdatedBy(userDetails.getUsername());
                    finalAssemblyRepository.save(finalAssembly);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Step Three added")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding FA Step Three")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFAStepThree(FAStepThreeUpdateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faStepThreeUpdateRequest -> {
                    log.info("Updating FA Step Three: {}", faStepThreeUpdateRequest.getUplNumber());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.uplNumber = :id", FinalAssembly.class);
                    query.setParameter("id", faStepThreeUpdateRequest.getUplNumber());

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }
                    if (faStepThreeUpdateRequest.getUdiNumber() != null) {
                        finalAssembly.setUdiNumber(faStepThreeUpdateRequest.getUdiNumber());
                    }
                    if (faStepThreeUpdateRequest.getAdapterCode() != null) {
                        finalAssembly.setAdapterCode(faStepThreeUpdateRequest.getAdapterCode());
                    }
                    finalAssembly.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    finalAssembly.setUpdatedBy(userDetails.getUsername());
                    finalAssemblyRepository.save(finalAssembly);
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Step Three updated")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while updating FA Step Three")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> addFACartoonPackage(FACartoonPackageAddRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faCartoonPackageAddRequest -> {
                    log.info("Adding FA Cartoon Package: {}", faCartoonPackageAddRequest.getUdiNumbers());

                    for (String udi : faCartoonPackageAddRequest.getUdiNumbers()) {
                        TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.udiNumber = :id", FinalAssembly.class);
                        query.setParameter("id", udi);

                        FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                        if (finalAssembly == null) {
                            return ResponseEntity.ok(ApiResponse.<Void>builder()
                                    .status("E1000")
                                    .statusDescription("Final Assembly not found in the system")
                                    .build());
                        }
                        finalAssembly.setCartoonNumber(faCartoonPackageAddRequest.getCartoonNumber());
                        finalAssemblyRepository.save(finalAssembly);
                    }

                    CartoonBox cartoonBox = cartoonBoxRepository.findByCartoonNumber(faCartoonPackageAddRequest.getCartoonNumber());
                    if (cartoonBox != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Cartoon Box found in the system")
                                .build());
                    }

                    cartoonBoxRepository.save(CartoonBox.builder()
                            .cartoonNumber(faCartoonPackageAddRequest.getCartoonNumber())
                            .createdAt(new Timestamp(System.currentTimeMillis()).toString())
                            .createdBy(userDetails.getUsername())
                            .build());

                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Cartoon Package added")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while adding FA Cartoon Package")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateCartoonPackageUdi(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Cartoon Package UDI: {}", validateRequest.getCode());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.udiNumber = :id", FinalAssembly.class);
                    query.setParameter("id", validateRequest.getCode());

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly != null && finalAssembly.getCartoonNumber() != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E2222")
                                .statusDescription("UDI already added to Cartoon Box " + finalAssembly.getCartoonNumber())
                                .build());
                    }
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E2000")
                                .statusDescription("UDI not found in the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("UDI not added to any Cartoon Box")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Cartoon Package UDI")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> updateFACartoonPackage(FACartoonPackageUpdateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(faCartoonPackageUpdateRequest -> {
                    log.info("Updating FA Cartoon Package: {}", faCartoonPackageUpdateRequest.getCartoonNumber());
                    CartoonBox cartoonBox = cartoonBoxRepository.findByCartoonNumber(faCartoonPackageUpdateRequest.getCartoonNumber());
                    if (cartoonBox == null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E1000")
                                .statusDescription("Cartoon Box not found in the system")
                                .build());
                    }
                    cartoonBox.setUpdatedAt(new Timestamp(System.currentTimeMillis()).toString());
                    cartoonBoxRepository.save(cartoonBox);

                    TypedQuery<FinalAssembly> query0 = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.cartoonNumber = :id", FinalAssembly.class);
                    query0.setParameter("id", faCartoonPackageUpdateRequest.getCartoonNumber());

                    List<FinalAssembly> finalAssemblies = finalAssemblyRepository.findByCustomQuery(query0);

                    for (FinalAssembly finalAssembly : finalAssemblies) {
                        finalAssembly.setCartoonNumber(null);
                        finalAssemblyRepository.save(finalAssembly);
                    }

                    for (String udi : faCartoonPackageUpdateRequest.getUdiNumbers()) {
                        TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.udiNumber = :id", FinalAssembly.class);
                        query.setParameter("id", udi);

                        FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                        if (finalAssembly == null) {
                            return ResponseEntity.ok(ApiResponse.<Void>builder()
                                    .status("E1000")
                                    .statusDescription("Final Assembly not found in the system")
                                    .build());
                        }
                        finalAssembly.setCartoonNumber(faCartoonPackageUpdateRequest.getCartoonNumber());
                        finalAssemblyRepository.save(finalAssembly);
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("FA Cartoon Package updated")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while updating FA Cartoon Package")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetFinalAssemblyByIdResponse>>> getFinalAssemblyByCartoonPackageUdi(String udi, UserDetails userDetails) {
        return Mono.just(udi)
                .map(udi1 -> {
                    log.info("Getting Final Assembly by Cartoon Package UDI: {}", udi1);
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.udiNumber = :id", FinalAssembly.class);
                    query.setParameter("id", udi1);

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<GetFinalAssemblyByIdResponse>builder()
                                .status("E1000")
                                .statusDescription("Final Assembly not found in the system")
                                .build());
                    }

                    CartoonBox cartoonBox = cartoonBoxRepository.findByCartoonNumber(finalAssembly.getCartoonNumber());

                    HHDevice device = hhDeviceRepository.findByCode(finalAssembly.getDeviceCode());

                    return ResponseEntity.ok(ApiResponse.<GetFinalAssemblyByIdResponse>builder()
                            .status("S1000")
                            .statusDescription("Final Assembly found")
                            .data(GetFinalAssemblyByIdResponse.builder()
                                    .finalAssembly(FinalAssemblyDto.builder()
                                            .deviceCode(finalAssembly.getDeviceCode())
                                            .category(finalAssembly.getCategory())
                                            .bladderCode(finalAssembly.getBladderCode())
                                            .uplNumber(finalAssembly.getUplNumber())
                                            .udiNumber(finalAssembly.getUdiNumber())
                                            .adapterCode(finalAssembly.getAdapterCode())
                                            .cartoonNumber(finalAssembly.getCartoonNumber())
                                            .createdAt(finalAssembly.getCreatedAt().toString())
                                            .updatedAt(finalAssembly.getUpdatedAt().toString())
                                            .updatedBy(finalAssembly.getUpdatedBy())
                                            .createdBy(finalAssembly.getCreatedBy())
                                            .build())
                                    .cartoonBox(cartoonBox != null ? CartoonBoxDto.builder()
                                            .cartoonNumber(finalAssembly.getCartoonNumber())
                                            .createdAt(cartoonBox.getCreatedAt())
                                            .updatedAt(cartoonBox.getUpdatedAt())
                                            .createdBy(cartoonBox.getCreatedBy())
                                            .build() : CartoonBoxDto.builder()
                                            .cartoonNumber("N/A")
                                            .createdAt(null)
                                            .updatedAt(null)
                                            .createdBy("N/A")
                                            .build()
                                    )
                                    .device(DeviceHHDto.builder()
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
                                    .build())
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<GetFinalAssemblyByIdResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while getting final assembly by Cartoon Package UDI")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetFinalAssemblyResponse>>> getFinalAssemblies(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting Final Assembly devices by pattern: {} by user: {}", req, userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return finalAssemblyRepository.findByCustomQuery(getCustomQuery(request, userDetails));
                    } else {
                        return finalAssemblyRepository.findByCustomQuery(getCustomQuery(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(finalAssemblies -> Mono.just(finalAssemblyRepository.countByCustomQuery(getCustomCountQuery(request, userDetails)))
                        .map(total -> ApiResponse.<GetFinalAssemblyResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetFinalAssemblyResponse.builder()
                                        .fas(getFADtosFromFAs(finalAssemblies))
                                        .total(total)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<GetFinalAssemblyResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while getting final assemblies")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<ValidateDeviceForStageTwoResponse>>> validateDeviceIdForStageTwo(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Device ID for Stage Two: {}", validateRequest.getCode());
                    HHDevice device = hhDeviceRepository.findByCode(validateRequest.getCode());
                    FinalAssembly finalAssembly = finalAssemblyRepository.findByDeviceId(validateRequest.getCode());

                    if (device == null && finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<ValidateDeviceForStageTwoResponse>builder()
                                .status("E2225")
                                .statusDescription("Device not found in the system")
                                .build());
                    }
                    if (device == null) {
                        return ResponseEntity.ok(ApiResponse.<ValidateDeviceForStageTwoResponse>builder()
                                .status("E2225")
                                .statusDescription("Device not validated")
                                .build());
                    }
                    if (finalAssembly == null) {
                        return ResponseEntity.ok(ApiResponse.<ValidateDeviceForStageTwoResponse>builder()
                                .status("E2225")
                                .statusDescription("Device not Validated with Stage One")
                                .build());
                    }
                    if (finalAssembly.getBladderCode() == null || finalAssembly.getUplNumber() == null) {
                        return ResponseEntity.ok(ApiResponse.<ValidateDeviceForStageTwoResponse>builder()
                                .status("S1000")
                                .statusDescription("Stage Two validation possible")
                                .data(ValidateDeviceForStageTwoResponse.builder()
                                        .finalAssembly(FinalAssemblyDto.builder()
                                                .deviceCode(finalAssembly.getDeviceCode())
                                                .category(finalAssembly.getCategory())
                                                .bladderCode(finalAssembly.getBladderCode())
                                                .uplNumber(finalAssembly.getUplNumber())
                                                .udiNumber(finalAssembly.getUdiNumber())
                                                .adapterCode(finalAssembly.getAdapterCode())
                                                .cartoonNumber(finalAssembly.getCartoonNumber())
                                                .createdAt(finalAssembly.getCreatedAt().toString())
                                                .updatedAt(finalAssembly.getUpdatedAt().toString())
                                                .updatedBy(finalAssembly.getUpdatedBy())
                                                .createdBy(finalAssembly.getCreatedBy())
                                                .build()
                                        ).build()
                                )
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<ValidateDeviceForStageTwoResponse>builder()
                            .status("E2222")
                            .statusDescription("Device validated for Stage Two")
                            .data(ValidateDeviceForStageTwoResponse.builder()
                                    .finalAssembly(FinalAssemblyDto.builder()
                                            .deviceCode(finalAssembly.getDeviceCode())
                                            .category(finalAssembly.getCategory())
                                            .bladderCode(finalAssembly.getBladderCode())
                                            .uplNumber(finalAssembly.getUplNumber())
                                            .udiNumber(finalAssembly.getUdiNumber())
                                            .adapterCode(finalAssembly.getAdapterCode())
                                            .cartoonNumber(finalAssembly.getCartoonNumber())
                                            .createdAt(finalAssembly.getCreatedAt().toString())
                                            .updatedAt(finalAssembly.getUpdatedAt().toString())
                                            .updatedBy(finalAssembly.getUpdatedBy())
                                            .createdBy(finalAssembly.getCreatedBy())
                                            .build()
                                    ).build()
                            )
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<ValidateDeviceForStageTwoResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating device for Stage Two")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateUplForStepThree(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating UPL for Step Three: {}", validateRequest.getCode());
                    TypedQuery<FinalAssembly> query = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE REPLACE(REPLACE(d.uplNumber, '\t', ''), ' ', '') = :id", FinalAssembly.class);
                    String normalizedCode = validateRequest.getCode().replaceAll("\\s+", "").trim();
                    query.setParameter("id", normalizedCode);

                    FinalAssembly finalAssembly = finalAssemblyRepository.findByCustomQueryForSingle(query);
                    if (finalAssembly != null && (finalAssembly.getUdiNumber() == null || finalAssembly.getAdapterCode() == null)) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("UPL not validated for Step Three")
                                .build());
                    } else if (finalAssembly != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E2222")
                                .statusDescription("Already Validated for Step Three")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("E2222")
                            .statusDescription("UPL number not found in the system")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating UPL for Step Three")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> validateCartoonPackageCartoonNumber(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(validateRequest -> {
                    log.info("Validating Cartoon Package Cartoon Number: {}", validateRequest.getCode());
                    TypedQuery<CartoonBox> query = entityManager.createQuery("SELECT d FROM CartoonBox d WHERE d.cartoonNumber = :id", CartoonBox.class);
                    query.setParameter("id", validateRequest.getCode());

                    CartoonBox cartoonBox = cartoonBoxRepository.findByCustomQueryForSingle(query);
                    if (cartoonBox != null) {
                        return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("E2222")
                                .statusDescription("Cartoon Package already added to the system")
                                .build());
                    }
                    return ResponseEntity.ok(ApiResponse.<Void>builder()
                            .status("S1000")
                            .statusDescription("Cartoon Package not added to the system")
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while validating Cartoon Package Cartoon Number")
                        .build())));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<GetCBByIdResponse>>> getCartoonBoxById(ValidateRequest request, UserDetails userDetails) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting Cartoon Box by pattern: {} by user: {}", req, userDetails.getUsername());
                    TypedQuery<CartoonBox> query = entityManager.createQuery("SELECT d FROM CartoonBox d WHERE d.cartoonNumber = :id", CartoonBox.class);
                    query.setParameter("id", request.getCode());

                    CartoonBox cartoonBox = cartoonBoxRepository.findByCustomQueryForSingle(query);
                    if (cartoonBox == null) {
                        return ResponseEntity.ok(ApiResponse.<GetCBByIdResponse>builder()
                                .status("E1000")
                                .statusDescription("Cartoon Box not found in the system")
                                .build());
                    }

                    TypedQuery<FinalAssembly> query0 = entityManager.createQuery("SELECT d FROM FinalAssembly d WHERE d.cartoonNumber = :id", FinalAssembly.class);
                    query0.setParameter("id", cartoonBox.getCartoonNumber());

                    List<FinalAssembly> finalAssemblies = finalAssemblyRepository.findByCustomQuery(query0);

                    return ResponseEntity.ok(ApiResponse.<GetCBByIdResponse>builder()
                            .status("S1000")
                            .statusDescription("Cartoon Box found")
                            .data(GetCBByIdResponse.builder()
                                    .uids(finalAssemblies.stream().map(FinalAssembly::getUdiNumber).collect(Collectors.toList()))
                                    .cartoonNumber(cartoonBox.getCartoonNumber())
                                    .build())
                            .build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.ok(ApiResponse.<GetCBByIdResponse>builder()
                        .status("E1000")
                        .statusDescription("Error occurred while getting Cartoon Box by pattern")
                        .build())));
    }

    private List<FinalAssemblyDto> getFADtosFromFAs(List<FinalAssembly> finalAssemblies) {
        List<FinalAssemblyDto> finalAssemblyDtos = new ArrayList<>();
        for (FinalAssembly finalAssembly : finalAssemblies) {
            finalAssemblyDtos.add(FinalAssemblyDto.builder()
                    .id(finalAssembly.getId())
                    .deviceCode(finalAssembly.getDeviceCode())
                    .category(finalAssembly.getCategory())
                    .bladderCode(finalAssembly.getBladderCode())
                    .uplNumber(finalAssembly.getUplNumber())
                    .udiNumber(finalAssembly.getUdiNumber())
                    .adapterCode(finalAssembly.getAdapterCode())
                    .cartoonNumber(finalAssembly.getCartoonNumber())
                    .createdAt(finalAssembly.getCreatedAt().toString())
                    .updatedAt(finalAssembly.getUpdatedAt().toString())
                    .updatedBy(finalAssembly.getUpdatedBy())
                    .createdBy(finalAssembly.getCreatedBy())
                    .build());
        }
        return finalAssemblyDtos;
    }

    private TypedQuery<FinalAssembly> getCustomQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT d FROM FinalAssembly d ");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<FinalAssembly> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY d.updatedAt DESC").toString(), FinalAssembly.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<Long> getCustomCountQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(d) FROM FinalAssembly d ");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private static void calculateFilterParts(GetByPatternRequest request, List<String> filterParts, UserDetails userDetails) {
        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            switch (request.getFilterType()) {
                case "DEVICE_CODE" -> filterParts.add("deviceCode LIKE '%" + request.getFilterValue() + "%'");
                case "CATEGORY" -> filterParts.add("category LIKE '%" + request.getFilterValue() + "%'");
                case "BLADDER_CODE" -> filterParts.add("bladderCode LIKE '%" + request.getFilterValue() + "%'");
                case "UPL_NUMBER" -> filterParts.add("uplNumber LIKE '%" + request.getFilterValue() + "%'");
                case "UDI_NUMBER" -> filterParts.add("udiNumber LIKE '%" + request.getFilterValue() + "%'");
                case "ADAPTER_CODE" -> filterParts.add("adapterCode LIKE '%" + request.getFilterValue() + "%'");
                case "CARTOON_NUMBER" -> filterParts.add("cartoonNumber LIKE '%" + request.getFilterValue() + "%'");
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
}
