package com.test_jig.test_jig_server.domain;

import com.test_jig.test_jig_server.dto.DeviceHHDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetHHDevicesResponse {
    private List<DeviceHHDto> devices;
    private long total;
}
