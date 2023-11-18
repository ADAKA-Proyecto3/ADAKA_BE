package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.dto.MedicalCenterDTO;
import com.cenfotec.adaka.app.dto.MetricDTO;
import com.cenfotec.adaka.app.dto.SensorDataDTO;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalCenterImpl implements MedicalCenterService {


    @Autowired
    private MedicalCenterRepository medicalCenterRepository; // Create this repository interface
    @Autowired
    private UserRepository userRepository; // Create this repository interface
    @Autowired
    private MetricsService metricsService;

    @Override
    public List<MedicalCenter> getAllMedicalCentersByUserId(int id) {
        List<MedicalCenter> medicalCenters = medicalCenterRepository.findByUserId(id);

        return medicalCenters;
    }

    @Override
    public MedicalCenter getMedicalCenterById(int id) {

        MedicalCenter medicalCenter = medicalCenterRepository.findById(id).orElseThrow(() -> new InvalidMedicalCenterException("no existe el centro médico: " + id));
        return medicalCenter;
    }

    @Override
    public MedicalCenter saveMedicalCenter(MedicalCenter medicalCenter, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new InvalidMedicalCenterException("usuario no encontrado"));

        if (medicalCenterRepository.existsMedicalCenterByNameAndUserId(medicalCenter.getName(), id)) {
            throw new InvalidMedicalCenterException("validación: ya existe un centro médico registrado con ese nombre");
        }
        // Realizar validación detallada
        List<String> validationErrors = validateMedicalCenter(medicalCenter, false);

        if (!validationErrors.isEmpty()) {
            throw new InvalidMedicalCenterException("validación, campos vacios: " + String.join(", ", validationErrors));
        }
        if (!validateUserMedicalCenter(user)) {
            throw new InvalidMedicalCenterException("máximo número de centros médicos registrados para el plan seleccionado");
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
            throw new InvalidMedicalCenterException("validación, campos vacios: " + String.join(", ", validationErrors));
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
            throw new InvalidMedicalCenterException("actualizar el estado del centro médico", ex);
        }
    }

    @Override
    public void deleteMedicalCenter(int id) {


        boolean hasRooms = medicalCenterRepository.existsMedicalCenterByIdAndRoomsIsNotEmpty(id);

        if (hasRooms) {
            throw new InvalidMedicalCenterException("el centro médico tiene salas asociadas");
        } else {
            medicalCenterRepository.deleteById(id);
        }

    }

    /**
     * Get all medical center info for map
     */
    @Override
    public List<MedicalCenterDTO> getAllMedicalCenters() {
        List<MedicalCenterDTO> medicalCenterDTOList = medicalCenterRepository.findAllMedicalCenters();

        List<MedicalCenterDTO> newMedicalCenterDTOList = new ArrayList<>();

        for (MedicalCenterDTO medialCenter : medicalCenterDTOList) {
            MedicalCenter centroMedico = getMedicalCenterById(medialCenter.getId());
            boolean hasDevice = false;

            for (Room room : centroMedico.getRooms()) {
                if (room.getDevice() != null) {
                    hasDevice = true;
                    Device device = room.getDevice();
                    List<MetricDTO> data = metricsService.getMetricsByRoom(device.getDeviceId());
                    List<SensorDataDTO> dataSensor = data.get(0).getSensorData();

                    for (SensorDataDTO sensor : dataSensor) {
                        if (sensor.getSensorName().equals("PM2.5")) {
                            medialCenter.setValue(String.valueOf(sensor.getValue()));
                        }
                    }
                }
            }

            if (hasDevice) {
                newMedicalCenterDTOList.add(medialCenter);
            }
        }


        return newMedicalCenterDTOList;
    }


    //PRIVATE METHODS
    private List<String> validateMedicalCenter(MedicalCenter medicalCenter, boolean update) {
        List<String> validate = new ArrayList<>();

        if (medicalCenter.getLatitude().isEmpty() || medicalCenter.getLongitude().isEmpty()) {
            validate.add("Coordenadas");
        }
        if (medicalCenter.getName().isEmpty()) {
            validate.add("Nombre");
        }
        if (medicalCenter.getDirection().isEmpty()) {
            validate.add("Dirección");
        }
        if (medicalCenter.getEmail().isEmpty()) {
            validate.add("Correo");
        }
        if (!update) {
            if (medicalCenter.getStatus() == null || medicalCenter.getStatus().equals("")) {
                validate.add("Estatus");
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
