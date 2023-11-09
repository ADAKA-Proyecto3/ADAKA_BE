package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface MedicalCenterService {

    List<MedicalCenter> getAllMedicalCentersByUserId(int id);
    MedicalCenter getMedicalCenterById(int id);
    MedicalCenter saveMedicalCenter(MedicalCenter medicalCenter, int id);
    MedicalCenter updateMedicalCenter(int id, MedicalCenter medicalCenter);
    MedicalCenter updateMedicalStatus(int id, String status);
    void deleteMedicalCenter(int id);
}
