package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Measure;
import com.cenfotec.adaka.app.domain.SensorData;
import com.cenfotec.adaka.app.dto.MetricDTO;
import com.cenfotec.adaka.app.dto.SensorDataDTO;
import com.cenfotec.adaka.app.exception.InvalidMetricException;
import com.cenfotec.adaka.app.repository.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricsService {
    @Autowired
    private MetricsRepository metricsRepository;


    public void addMetrics(MetricDTO metricDTO) {
        try {
            Measure measure = convertToEntity(metricDTO);
            metricsRepository.save(measure);
        } catch (Exception e) {
            throw new InvalidMetricException("Error al guardar las métricas: " + e.getMessage(), e);
        }
    }

    private Measure convertToEntity(MetricDTO metricDTO) {
        Measure measure = new Measure();
        measure.setDeviceId(metricDTO.getDeviceId());
        measure.setTimestamp(LocalDateTime.now());

        // Convertir SensorDataDTO a SensorData
        List<SensorData> sensorDataList = metricDTO.getSensorData().stream()
                .map(dto -> convertSensorDataToEntity(dto, measure))
                .collect(Collectors.toList());

        measure.setSensorData(sensorDataList);

        return measure;
    }

    private SensorData convertSensorDataToEntity(SensorDataDTO sensorDataDTO, Measure measure) {
        SensorData sensorData = new SensorData();
        sensorData.setValue(sensorDataDTO.getValue());
        sensorData.setUnit(sensorDataDTO.getUnit());
        sensorData.setSensorName(sensorDataDTO.getSensorName());
        sensorData.setMeasure(measure);
        return sensorData;
    }


    public List<MetricDTO> getMetricsByRoom(int roomId) {
        try {
            List<Measure> measures = metricsRepository.findAllByRoomId(roomId);
            return measures.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidMetricException("Error al obtener métricas por habitación: " + e.getMessage(), e);
        }
    }

    private MetricDTO convertToDTO(Measure measure) {
        MetricDTO metricDTO = new MetricDTO();
        metricDTO.setDeviceId(measure.getDeviceId());
        // Asumiendo que tienes un campo para el roomId en Measure o relacionado
        // metricDTO.setRoomId(measure.getRoom().getId());

        List<SensorDataDTO> sensorDataDTOs = measure.getSensorData().stream()
                .map(this::convertSensorDataToDTO)
                .collect(Collectors.toList());
        metricDTO.setSensorData(sensorDataDTOs);

        return metricDTO;
    }

    private SensorDataDTO convertSensorDataToDTO(SensorData sensorData) {
        SensorDataDTO sensorDataDTO = new SensorDataDTO();
        sensorDataDTO.setValue(sensorData.getValue());
        sensorDataDTO.setUnit(sensorData.getUnit());
        sensorDataDTO.setSensorName(sensorData.getSensorName());
        return sensorDataDTO;
    }

    public List<MetricDTO> getMetricsByRoomAndDates(int roomId, LocalDate startDate, LocalDate endDate) {
        // Lógica para obtener métricas por habitación y rango de fechas
        // Convierte las entidades a MetricDTO y retórnalas
        // Lanza InvalidMetricException si hay un error
        return null;
    }
}