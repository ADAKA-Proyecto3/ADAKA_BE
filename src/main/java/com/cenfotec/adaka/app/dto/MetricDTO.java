package com.cenfotec.adaka.app.dto;

import lombok.Data;
import java.util.List;

@Data
public class MetricDTO {
    private List<SensorDataDTO> sensorData;
    private int deviceId;
}
