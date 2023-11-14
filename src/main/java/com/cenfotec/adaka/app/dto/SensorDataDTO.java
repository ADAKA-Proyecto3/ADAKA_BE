package com.cenfotec.adaka.app.dto;

import lombok.Data;

@Data
public class SensorDataDTO {
    private double value;
    private String unit;
    private String sensorName;
}
