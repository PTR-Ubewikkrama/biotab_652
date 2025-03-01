package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hh_device")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HHDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;

    private String deviceCode;

    private String deviceCodeStatus;

    private String pcbTestCode;

    private String pcbTestCodeStatus;

    private String valveTestOneCode;

    private String valveTestOneCodeStatus;

    private String valveTestTwoCode;

    private String valveTestTwoCodeStatus;

    private String airPumpTestCode;

    private String airPumpTestCodeStatus;

    private String latchButtonTestCode;

    private String latchButtonTestCodeStatus;

    private String overPressureValveTestCode;

    private String overPressureValveTestCodeStatus;

    private String batteryTestCode;

    private String batteryTestCodeStatus;

    private String enclosureCode;

    private String enclosureCodeStatus;

    private String airBladderCode;

    private String airBladderCodeStatus;

    private String powerSupplyTestCode;

    private String powerSupplyTestCodeStatus;

    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}