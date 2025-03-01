package com.test_jig.test_jig_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "valve_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValveTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private String qrCode;

    private double airChamberLoadingPressure;

    private boolean airChamberStatus;

    private double v1OutletPressureAfter10MsOnTime;

    private boolean v1OutletOnStatus;

    private double v1OutletPressureAfter10MsOffTime;

    private boolean v1OutletOffStatus;

    private double v2OutletPressureAfter10MsOnTime;

    private boolean v2OutletOnStatus;

    private double v2OutletPressureAfter10MsOffTime;

    private boolean v2OutletOffStatus;

    private double v3OutletPressureAfter10MsOnTime;

    private boolean v3OutletOnStatus;

    private double v3OutletPressureAfter10MsOffTime;

    private boolean v3OutletOffStatus;

    private boolean valveStatus;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}