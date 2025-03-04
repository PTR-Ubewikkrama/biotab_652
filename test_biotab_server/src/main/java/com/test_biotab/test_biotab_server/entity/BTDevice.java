package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bt_device")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "device_code")
    private String deviceCode;

    private String powerPcbCode;
    private String powerPcbCodeStatus;
    private String pumpCode;
    private String pumpCodeStatus;
    private String fanCode;
    private String fanCodeStatus;
    private String uiPcbCode;
    private String uiPcbCodeStatus;
    private String encoderCode;
    private String encoderCodeStatus;
    private String mainPcbCode;
    private String mainPcbCodeStatus;
    private String manifoldCode;
    private String manifoldCodeStatus;
    private String valveCardInsideCableSetCode;
    private String valveCardInsideCableSetCodeStatus;
    private String valveCardInputOutputCableSetCode;
    private String valveCardInputOutputCableSetCodeStatus;
    private String overPressureValveCode;
    private String overPressureValveCodeStatus;
    private String powerCableCode;
    private String uiCableCode;
    private String displayCode;
    private String frontBracketAssemblyCode;
    private String frontBracketAssemblyCodeStatus;
    private String powerAdaptorCode;
    private String powerAdaptorCodeStatus;
    private String enclosureTopCode;
    private String enclosureBottomCode;
    private String backVentCode;
    private String fanMountCode;
    private String encoderSupporterCode;
    private String pcbHolderCode;

    private String createdBy;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}