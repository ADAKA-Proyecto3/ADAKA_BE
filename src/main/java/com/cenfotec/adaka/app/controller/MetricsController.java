package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.Response;
import com.cenfotec.adaka.app.dto.MetricDTO;
import com.cenfotec.adaka.app.exception.InvalidMetricException;
import com.cenfotec.adaka.app.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricsController {
    @Autowired
    private MetricsService metricsService;
    private Logger log = LoggerFactory.getLogger(MetricsController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> addMetrics(@RequestBody MetricDTO metricDTO) {
        log.debug("addMetrics method started");
        try {
            metricsService.addMetrics(metricDTO);
            return ResponseEntity.ok(new Response<>("Éxito", Collections.singletonList(metricDTO)));
        } catch (InvalidMetricException ex) {
            log.error("Error al añadir métricas: " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
        }
    }

    @GetMapping(value = "/room/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> getMetricsByRoom(@PathVariable int roomId) {
        log.debug("getMetricsByRoom method started");
        try {
            List<MetricDTO> metrics = metricsService.getMetricsByRoom(roomId);
            return ResponseEntity.ok(new Response<>("Éxito", metrics));
        } catch (InvalidMetricException ex) {
            log.error("Error al obtener métricas por habitación: " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
        }
    }


    @GetMapping(value = "/room/{roomId}/dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Map<String, List<Object>>>> getMetricsByRoomAndDates(
            @PathVariable int roomId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.debug("getMetricsByRoomAndDates method started");
        try {
            Map<String, List<Object>> metrics = metricsService.getMetricsByRoomAndDates(roomId, startDate, endDate);
            return ResponseEntity.ok(Response.<Map<String, List<Object>>>builder()
                    .title("Éxito")
                    .data(List.of(metrics))
                    .build());
        } catch (InvalidMetricException ex) {
            log.error("Error al obtener métricas por habitación y fechas: " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.<Map<String, List<Object>>>builder()
                            .title("Error")
                            .data(null)
                            .build());
        }
    }

}
