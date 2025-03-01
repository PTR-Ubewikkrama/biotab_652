package com.test_biotab.test_biotab_server.domain;

import com.test_biotab.test_biotab_server.dto.DeviceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDevicesResponse {
    private List<DeviceDto> devices;
    private long total;
    private long totalFailed;
}
