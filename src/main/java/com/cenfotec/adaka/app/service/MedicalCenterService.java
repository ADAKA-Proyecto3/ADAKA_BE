package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface MedicalCenterService {

    List<MedicalCenter> getAllMedicalCenters(int id);
    MedicalCenter getMedicalCenterById(int id);
    MedicalCenter saveMedicalCenter(MedicalCenter medicalCenter, int id);
    void updateMedicalCenter(int id, MedicalCenter medicalCenter);
    void updateMedicalStatus(int id, String status);
}
