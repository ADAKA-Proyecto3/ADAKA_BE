package com.cenfotec.adaka.app.controller;


import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Response;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.exception.InvalidUserException;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.UserService;
import com.cenfotec.adaka.app.service.impl.MedicalCenterImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medical")// controller
@RequiredArgsConstructor
public class MedicalCenterController {
    @Autowired
    private MedicalCenterService medicalCenterService;
    private Logger log = LoggerFactory.getLogger(MedicalCenterController.class);

    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> getAllMedicalCenters(@PathVariable int id) {
        log.debug("get all MedicalCenter method  started");
        try {
            List<MedicalCenter> medicalCenters = medicalCenterService.getAllMedicalCenters(id);
            return ResponseEntity.ok(new Response<>("Éxito", medicalCenters));

        } catch (InvalidMedicalCenterException ex) {
            // Manejo específico para la excepción InvalidMedicalCenterException
            log.error("Error al obtener los centros medicos del usuario: " + ex.getMessage(), ex);

            if (ex.getMessage().contains("Validation errors:")) {
                // Manejo específico para el caso de "No existe el ID"
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Error, " + ex.getMessage(), null));
            } else {
                // Manejo general para otras excepciones
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
            }
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> getMedicalCenterById(@PathVariable int id) {
        log.debug("getMedicalCenterById method started");
        try {
            MedicalCenter medicalCenter = medicalCenterService.getMedicalCenterById(id);
            return ResponseEntity.ok(new Response<>("Éxito", Collections.singletonList(medicalCenter)));
        } catch (InvalidMedicalCenterException ex) {
            // Manejo específico para la excepción InvalidMedicalCenterException
            log.error("Error al obtener el centro médico por ID: " + ex.getMessage(), ex);

            if (ex.getMessage().contains("The ID does not exist:")) {
                // Manejo específico para el caso de "No existe el ID"
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Error, " + ex.getMessage(), null));
            } else {
                // Manejo general para otras excepciones
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
            }
        }
    }


    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> saveMedicalCenter(@RequestBody MedicalCenter medicalCenter, @PathVariable int id) {
        log.debug("saveMedicalCenter method  started");
        try {

            MedicalCenter newMedicalCenter = medicalCenterService.saveMedicalCenter(medicalCenter, id);

            return ResponseEntity.ok(new Response<>("Éxito", Collections.singletonList(newMedicalCenter)));
        } catch (InvalidMedicalCenterException ex) {

            log.error("Error al crear el centro médico: " + ex.getMessage(), ex.getCause());

            if (ex.getMessage().contains("Some fields are empty: ")) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>("Error, " + ex.getMessage(), null));

            } else {
                // Manejo general para otras excepciones
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
            }
        }
    }

    @PutMapping(value = "changeMedical/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> updateMedicalCenter(@PathVariable int id, @RequestBody MedicalCenter medicalCenter) {
        log.debug("update MedicalCenter method started");

        try {
            medicalCenterService.updateMedicalCenter(id, medicalCenter);
            return ResponseEntity.ok(new Response<>("Éxito", Collections.singletonList(medicalCenter)));
        } catch (InvalidMedicalCenterException ex) {
            // Manejo de la excepción específica InvalidMedicalCenterException
            log.error("Error al actualizar el centro médico: " + ex.getMessage(), ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
        }
    }

    @PutMapping(value = "/changeMedicalStatus/{id}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMedicalCenterStatus(@PathVariable int id, @PathVariable String status) {
        log.debug("update status MedicalCenter method  started");
        MedicalCenter existingmedical = medicalCenterService.getMedicalCenterById(id);
        if (existingmedical != null) {
            medicalCenterService.updateMedicalStatus(id, status);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
