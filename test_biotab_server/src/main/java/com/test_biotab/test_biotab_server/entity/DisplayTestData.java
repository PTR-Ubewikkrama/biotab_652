package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "display_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisplayTestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    private String serialNumber;
    private Boolean physicalInspectionState;
    private Boolean backLightOn;
    private Boolean redScreenOn;
    private Boolean greenScreenOn;
    private Boolean blueScreenOn;
    private Boolean colorPatch;
    private Boolean BTDisplayText;
    private Boolean screenOff;
    private Boolean overallDisplayStatus;
    private Boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
