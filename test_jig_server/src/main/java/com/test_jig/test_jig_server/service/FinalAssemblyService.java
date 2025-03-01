package com.test_jig.test_jig_server.service;

import com.test_jig.test_jig_server.domain.ApiResponse;
import com.test_jig.test_jig_server.domain.GetByPatternRequest;
import com.test_jig.test_jig_server.domain.ValidateRequest;
import com.test_jig.test_jig_server.domain.fa.*;
import com.test_jig.test_jig_server.dto.fa.FinalAssemblyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface FinalAssemblyService {
    Mono<ResponseEntity<ApiResponse<Void>>> validateDeviceId(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<FinalAssemblyDto>>> getFinalAssemblyById(String deviceId, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> addFAStepOne(FAStepOneAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> updateFAStepOne(FAStepOneUpdateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateBladder(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateUplNumber(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> addFAStepTwo(FAStepTwoAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> updateFAStepTwo(FAStepTwoUpdateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateUdi(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateAdapter(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> addFAStepThree(FAStepThreeAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> updateFAStepThree(FAStepThreeUpdateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> addFACartoonPackage(FACartoonPackageAddRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateCartoonPackageUdi(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> updateFACartoonPackage(FACartoonPackageUpdateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<GetFinalAssemblyByIdResponse>>> getFinalAssemblyByCartoonPackageUdi(String udi, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<GetFinalAssemblyResponse>>> getFinalAssemblies(GetByPatternRequest request, UserDetails userDetails, String pageNo);

    Mono<ResponseEntity<ApiResponse<ValidateDeviceForStageTwoResponse>>> validateDeviceIdForStageTwo(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateUplForStepThree(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<Void>>> validateCartoonPackageCartoonNumber(ValidateRequest request, UserDetails userDetails);

    Mono<ResponseEntity<ApiResponse<GetCBByIdResponse>>> getCartoonBoxById(ValidateRequest request, UserDetails userDetails);
}
