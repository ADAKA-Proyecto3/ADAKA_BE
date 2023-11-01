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
    private UserRepository userRepository; // Create this repository interface

    @Override
    public List<MedicalCenter> getAllMedicalCentersByUserId(int id) {
        List<MedicalCenter> medicalCenters = medicalCenterRepository.findByUserId(id);

        return medicalCenters;
    }

    @Override
    public MedicalCenter getMedicalCenterById(int id) {

        MedicalCenter medicalCenter = medicalCenterRepository.findById(id).orElseThrow(() -> new InvalidMedicalCenterException("The ID does not exist: " + id));
        return medicalCenter;
    }

    @Override
    public MedicalCenter saveMedicalCenter(MedicalCenter medicalCenter, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new InvalidMedicalCenterException("Not user with ID"));

        if (medicalCenterRepository.existsMedicalCenterByNameAndUserId(medicalCenter.getName(), id)) {
            throw new InvalidMedicalCenterException("Validation errors: A medical center with that name already exists.");
        }
        // Realizar validación detallada
        List<String> validationErrors = validateMedicalCenter(medicalCenter, false);

        if (!validationErrors.isEmpty()) {
            throw new InvalidMedicalCenterException("Validation errors: " + String.join(", ", validationErrors));
        }
        if (!validateUserMedicalCenter(user)) {
            throw new InvalidMedicalCenterException("Maximum number of medical centers according to the user's plan");
        }
        // Guardar el centro médico en la base de datos
        MedicalCenter newMedicalCenter = medicalCenterRepository.save(medicalCenter);

        // Asociar el centro médico al usuario
        associateMedicalCenterWithUser(newMedicalCenter, user);

        return newMedicalCenter;
    }

    @Override
    public MedicalCenter updateMedicalCenter(int id, MedicalCenter newMedicalCenter) {

        List<String> validationErrors = validateMedicalCenter(newMedicalCenter, true);

        if (!validationErrors.isEmpty()) {
            throw new InvalidMedicalCenterException("Validation errors: " + String.join(", ", validationErrors));
        }
        //Get the old medical center and update the relational values
        MedicalCenter oldMedicalCenter = getMedicalCenterById(id);
        newMedicalCenter.setStatus(oldMedicalCenter.getStatus());
        newMedicalCenter.setUser(oldMedicalCenter.getUser());
        newMedicalCenter.setId(id);

        return medicalCenterRepository.save(newMedicalCenter);
    }

    @Override
    public MedicalCenter updateMedicalStatus(int id, String status) {
        try {
            MedicalCenter medicalCenter = getMedicalCenterById(id);
            Status newStatus = Status.valueOf(status);
            medicalCenter.setStatus(newStatus);
            return medicalCenterRepository.save(medicalCenter);
        } catch (Exception ex) {
            throw new InvalidMedicalCenterException("Error al actualizar el estado del centro médico", ex);
        }
    }

    @Override
    public void deleteMedicalCenter(int id) {


        boolean hasRooms = medicalCenterRepository.existsMedicalCenterByIdAndRoomsIsNotEmpty(id);

        if (hasRooms) {
            throw new InvalidMedicalCenterException("You must remove the rooms associated with this medical center.");
        } else {
            medicalCenterRepository.deleteById(id);
        }

    }


    //PRIVATE METHODS
    private List<String> validateMedicalCenter(MedicalCenter medicalCenter, boolean update) {
        List<String> validate = new ArrayList<>();

        if (medicalCenter.getLatitude().isEmpty() || medicalCenter.getLongitude().isEmpty()) {
            validate.add("Coordinades");
        }
        if (medicalCenter.getName().isEmpty()) {
            validate.add("Name");
        }
        if (medicalCenter.getDirection().isEmpty()) {
            validate.add("Direction");
        }
        if (medicalCenter.getEmail().isEmpty()) {
            validate.add("Email");
        }
        if (!update) {
            if (medicalCenter.getStatus() == null || medicalCenter.getStatus().equals("")) {
                validate.add("Status");
            }
        }

        return validate;
    }

    private void associateMedicalCenterWithUser(MedicalCenter medicalCenter, User user) {
        medicalCenter.setUser(user);
        medicalCenterRepository.save(medicalCenter);
    }

    private boolean validateUserMedicalCenter(User user) {
        List<MedicalCenter> medicalCenters = user.getMedicalCenters();
        //AQUI SE LE LA VARA
        return true;
    }
}
