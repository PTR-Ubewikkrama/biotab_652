package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mani_fold_leak_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManiFoldLeakTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    private String qrCode;
    private Boolean physicalInspectionState;
    private Double leakageFlowrate;
    private Boolean manifoldLeakState;
    private Boolean overallManifoldLeakState;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
