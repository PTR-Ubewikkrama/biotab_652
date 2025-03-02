package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "power_pcb_v2_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PowerPCBV2TestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    private Double loadVoltageLowThresh;
    private String serialNumber;
    private Double loadCurrentLowThresh;
    private String usbCPowerOutletConnectivity;
    private Double loadVoltage;
    private Boolean loadVoltageStatus;
    private Boolean loadCurrentStatus;
    private Double loadCurrent;
    private Boolean deviceStatus;
    private Boolean noiseLevelStatus;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
