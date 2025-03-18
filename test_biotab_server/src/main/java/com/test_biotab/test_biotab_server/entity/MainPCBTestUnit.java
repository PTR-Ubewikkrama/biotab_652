package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "main_pcb_test_unit")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPCBTestUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "main_test_id")
    private int mainTestId;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_type")
    private String testType;

    @Column(name = "validation_type")
    private String validationType;

    @Column(name = "actual_value")
    private String actualValue;

    @Column(name = "min_value")
    private double minValue;

    @Column(name = "max_value")
    private double maxValue;

    @Column(name = "unit")
    private String unit;

    @Column(name = "status")
    private boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
