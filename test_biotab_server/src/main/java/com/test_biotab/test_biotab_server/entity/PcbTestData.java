package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pcb_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PcbTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    private String serialNumber;

    private boolean chargePortConnectStatus;

    private boolean intensityButtonStatus;

    private boolean runPauseButtonStatus;

    private boolean modeButtonStatus;

    private boolean greenLedStatus;

    private boolean blueLedStatus;

    private boolean redLedRingStatus;

    private boolean greenLedRingStatus;

    private boolean blueLedRingStatus;

    private boolean whiteLedRingStatus;

    private boolean ledRingOffStatus;

    private boolean buzzerStatus;

    private boolean latchSwitchLedOnStatus;

    private boolean latchSwitchOffStatus;

    private boolean latchSwitchLedOffStatus;

    private boolean latchSwitchOnStatus;

    private boolean chargerPortDisconnectStatus;

    private boolean pumpStatus;

    private boolean batteryChargingStatus;

    private double startBatteryChargingPercentage;

    private double endBatteryChargingPercentage;

    private boolean batteryTemperatureStatus;

    private double batteryTemperature;

    private boolean pressurePathStatus;

    private double startPressurePathValue;

    private double endPressurePathValue;

    private boolean pressureSensorStatus;

    private double startPressureSensorValue;

    private double endPressureSensorValue;

    private boolean valve01Status;

    private double valve01StartValue;

    private double valve01EndValue;

    private boolean valve02Status;

    private double valve02StartValue;

    private double valve02EndValue;

    private boolean valve03Status;

    private double valve03StartValue;

    private double valve03EndValue;

    private boolean valve04Status;

    private double valve04StartValue;

    private double valve04EndValue;

    private boolean valve05Status;

    private double valve05StartValue;

    private double valve05EndValue;

    private boolean valve06Status;

    private double valve06StartValue;

    private double valve06EndValue;

    private boolean phaseTwoValve01Status;

    private double phaseTwoValve01StartValue;

    private double phaseTwoValve01EndValue;

    private boolean phaseTwoValve02Status;

    private double phaseTwoValve02StartValue;

    private double phaseTwoValve02EndValue;

    private boolean phaseTwoValve03Status;

    private double phaseTwoValve03StartValue;

    private double phaseTwoValve03EndValue;

    private boolean phaseTwoValve04Status;

    private double phaseTwoValve04StartValue;

    private double phaseTwoValve04EndValue;

    private boolean phaseTwoValve05Status;

    private double phaseTwoValve05StartValue;

    private double phaseTwoValve05EndValue;

    private boolean phaseTwoValve06Status;

    private double phaseTwoValve06StartValue;

    private double phaseTwoValve06EndValue;

    private boolean phaseTwoPressureSensorStatus;

    private boolean phaseTwoPumpStatus;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}