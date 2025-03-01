package com.test_jig.test_jig_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "over_pressure_valve_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverPressureValveTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    private String qrCode;

    private Double maxPressure;

    private Double maxPressureTime;

    private Double maxPressureFlowRate;

    private Double normalPressure;

    private Double normalPressureTime;

    private Double normalPressureFlowRate;

    private Boolean overPressureValveStatus;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
