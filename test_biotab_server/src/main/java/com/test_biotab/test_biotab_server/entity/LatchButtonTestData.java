package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "latch_button_test_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatchButtonTestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    private String qrCode;

    private Boolean buttonOnTestStatus;

    private Boolean buttonOffTestStatus;

    private Boolean ledOnTestStatus;

    private Boolean latchButtonStatus;

    private Boolean status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;
}
