package com.test_biotab.test_biotab_server.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "valve_card_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValveCardTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    private String qrCode;
    private Boolean physicalInspectionState;
    private String rail;
    private String valve1;
    private String valve3;
    private String valve5;
    private String valve7;
    private String valve2;
    private String valve4;
    private String valve6;
    private String valve8;
    private String amperageTest;
    private String shiftRegisterTest;
    private String overallValveCardState;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
