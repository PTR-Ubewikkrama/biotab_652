package com.test_jig.test_jig_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LatchButtonTestDto {
    private Integer testId;

    private String qrCode;

    private String deviceMac;

    private Boolean buttonOnTestStatus;

    private Boolean buttonOffTestStatus;

    private Boolean ledOnTestStatus;

    private Boolean latchButtonStatus;

    private LocalDateTime dateTime;
}
