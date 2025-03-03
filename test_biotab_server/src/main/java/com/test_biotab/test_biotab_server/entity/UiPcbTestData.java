package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ui_pcb_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UiPcbTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    private String serialNumber;
    private Boolean physicalInspectionState;
    private Boolean redLedState;
    private Boolean whiteLedState;
    private Boolean ledRingOnState;
    private Boolean ledRingFadeState;
    private Boolean overallUiPcbState;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
