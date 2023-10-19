package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.UserService;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalCenterImpl implements MedicalCenterService {


    @Autowired
    private MedicalCenterRepository medicalCenterRepository; // Create this repository interface
    @Autowired
    private UserService userService; // Create this repository interface

    @Override
    public List<MedicalCenter> getAllMedicalCenters(int id) {
        User user = userService.getUserById(id);
        List<MedicalCenter> medicalCenters = user.getMedicalCenters();

        return medicalCenters;
    }

    @Override
    public MedicalCenter getMedicalCenterById(int id) {

        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findById(id);
        if (optionalMedicalCenter.isPresent()) {
            return optionalMedicalCenter.get();
        } else {
            throw new InvalidMedicalCenterException("The ID does not exist: " + id);
        }
    }

    @Override
    public MedicalCenter saveMedicalCenter(MedicalCenter medicalCenter, int id) {
        User user =  userService.getUserById(id);
        // Realizar validación detallada
        List<String> validationErrors = validateMedicalCenter(medicalCenter);

        if (!validationErrors.isEmpty()) {
            throw new InvalidMedicalCenterException("Validation errors: " + String.join(", ", validationErrors));
        }
        if(!validateUserMedicalCenter(user)){
            throw new InvalidMedicalCenterException("Maximum number of medical centers according to the user's plan");
        }
        // Guardar el centro médico en la base de datos
        MedicalCenter newMedicalCenter = medicalCenterRepository.save(medicalCenter);

        // Asociar el centro médico al usuario
        associateMedicalCenterWithUser(newMedicalCenter, user);

        return newMedicalCenter;
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
        try {
            medicalCenterRepository.save(medicalCenter);
        } catch (Exception ex) {
            throw new InvalidMedicalCenterException("Error al actualizar el estado del centro médico", ex);
        }
    }


    //PRIVATE METODS
    private List<String> validateMedicalCenter(MedicalCenter medicalCenter) {
        List<String> validate = new ArrayList<>();

        if (medicalCenter.getCoordinates().isEmpty()) {
            validate.add("Coordinades");
        }
        if (medicalCenter.getName() == null || medicalCenter.getName().equals("")) {
            validate.add("Name");
        }
        if (medicalCenter.getDirection() == null || medicalCenter.getDirection().equals("")) {
            validate.add("Direction");
        }
        if (medicalCenter.getEmail() == null || medicalCenter.getEmail().equals("")) {
            validate.add("Email");
        }
        if (medicalCenter.getStatus() == null || medicalCenter.getStatus().equals("")) {
            validate.add("Status");
        }

        return validate;
    }
    private void associateMedicalCenterWithUser(MedicalCenter medicalCenter, User user) {
        medicalCenter.setUser(user);
        medicalCenterRepository.save(medicalCenter);
    }
    private boolean validateUserMedicalCenter(User user){
       List<MedicalCenter> medicalCenters = user.getMedicalCenters();
       //AQUI SE LE LA VARA
       return true;
    }
}
