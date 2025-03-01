package com.test_biotab.test_biotab_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String statusDescription;
    private T data;

    public ApiResponse(String status, String statusDescription) {
        this.status = status;
        this.statusDescription = statusDescription;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success() {
        return ResponseEntity.ok(new ApiResponse<>("S1000", "Request successful"));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus httpStatus, String errorStatus, String message) {
        return ResponseEntity.status(httpStatus).body(new ApiResponse<>(errorStatus, message));
    }
}
