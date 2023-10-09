package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MedicalCenterImpl implements MedicalCenterService {


    @Autowired
    private MedicalCenterRepository medicalCenterRepository; // Create this repository interface

    @Override
    public List<MedicalCenter> getAllMedicalCenters() {
        return medicalCenterRepository.findAll();
    }

    @Override
    public MedicalCenter getMedicalCenterById(int id) {
        return medicalCenterRepository.findById(id).orElse(null);
    }

    @Override
    public void saveMedicalCenter(MedicalCenter medicalCenter) {
        medicalCenterRepository.save(medicalCenter);
    }

    @Override
    public void updateMedicalCenter(int id, MedicalCenter medicalCenter) {
        medicalCenter.setId(id);
        medicalCenterRepository.save(medicalCenter);
    }
    @Override
    public void updateMedicalStatus(int id, String status) {
        MedicalCenter medicalCenter = getMedicalCenterById(id);
        Status newStatus = Status.valueOf(status);
        medicalCenter.setStatus(newStatus);
    }
}
