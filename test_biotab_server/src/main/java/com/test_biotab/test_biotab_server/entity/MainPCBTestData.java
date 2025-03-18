package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "main_pcb_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPCBTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "software_version")
    private String softwareVersion;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "test_id")
    private String testId;

    private boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
