package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "power_pcb_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PowerPCBTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    private Double powerGroundResistanceUpperLimit;
    private String serialNumber;
    private Boolean dcBarrelJackConnectivityStatus;
    private Boolean usbCPowerOutletConnectivity;
    private Double powerGroundResistance;
    private Boolean powerGroundResistanceStatus;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
