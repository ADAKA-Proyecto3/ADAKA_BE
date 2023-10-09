package com.cenfotec.adaka.app.controller;


import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical")// controller
@RequiredArgsConstructor
public class MedicalCenterController {

    private MedicalCenterService medicalCenterService;
    private Logger log = LoggerFactory.getLogger(UserController.class);



    @GetMapping("/all")
    public ResponseEntity<List<MedicalCenter>> getAllMedicalCenters() {
        log.debug("get all ser method  started");
        List<MedicalCenter> medicalCenters = medicalCenterService.getAllMedicalCenters();
        return ResponseEntity.ok(medicalCenters);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalCenter> getMedicalCenterById(@PathVariable int id) {
        log.debug("getUserById method  started");
        MedicalCenter medicalCenter = medicalCenterService.getMedicalCenterById(id);
        if (medicalCenter != null) {
            return ResponseEntity.ok(medicalCenter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveMedicalCenter(@RequestBody MedicalCenter medicalCenter) {
        log.debug("saveUser method  started");
        medicalCenterService.saveMedicalCenter(medicalCenter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}/changeMedical", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMedicalCenter(@PathVariable int id, @RequestBody MedicalCenter medicalCenter) {
        log.debug("update User method  started");
        MedicalCenter existingmedical = medicalCenterService.getMedicalCenterById(id);
        if (existingmedical != null) {
            medicalCenterService.updateMedicalCenter(id, medicalCenter);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value = "/changeMedicalStatus/{id}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMedicalCenterStatus(@PathVariable int id, @PathVariable String status) {
        log.debug("update User method  started");
        MedicalCenter existingmedical = medicalCenterService.getMedicalCenterById(id);
        if (existingmedical != null) {
            medicalCenterService.updateMedicalStatus(id, status);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
