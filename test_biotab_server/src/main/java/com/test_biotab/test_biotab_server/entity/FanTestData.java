package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "fan_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FanTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    private String qrCode;
    private String visualInspection;
    private Double drawCurrent;
    private Boolean drawCurrentState;
    private Double fanSpeed;
    private Boolean fanSpeedState;
    private Boolean overallFanState;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
