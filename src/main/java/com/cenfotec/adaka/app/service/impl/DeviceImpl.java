package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.exception.InvalidRoomException;
import com.cenfotec.adaka.app.repository.DeviceRepository;
import com.cenfotec.adaka.app.repository.RoomRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceImpl implements DeviceService {


    @Autowired
    private DeviceRepository deviceRepository; // Create this repository interface
    @Autowired
    private RoomRepository roomRepository; // Create this repository interface

    @Override
    public List<Device> getAllDevicesByUserId(int id) {
        List<Device> devices = deviceRepository.findByUserId(id);
        return devices;
    }

    @Override
    public Device getDeviceById(int id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new InvalidMedicalCenterException("No existe el cdispositivo: " + id));
        return device;
    }

    @Override
    public Device saveDevice(Device device, int id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new InvalidRoomException("Sala no encontrada");

        if (deviceRepository.existsDeviceByDeviceIdAndUserId(device.getDeviceId(), id)) {
            throw new InvalidMedicalCenterException("Validación: ya existe un dispositivo registrado con esa identificacion.");
        }
        // Realizar validación detallada
        List<String> validationErrors = validateDevice(device, false);

        if (!validationErrors.isEmpty()) {
            throw new InvalidMedicalCenterException("Validación, campos vacios: " + String.join(", ", validationErrors));
        }
       // if (!validateUserMedicalCenter(user)) {
       //     throw new InvalidMedicalCenterException("Máximo número de centros médicos registrados para el plan seleccionado");
       // }
        // Guardar el device en la base de datos
        Device newDevice = deviceRepository.save(device);

        // Asociar el centro médico al usuario
        associateDeviceWithRoom(newDevice, room);

        return newDevice;
    }

    @Override
    public MedicalCenter updateMedicalCenter(int id, MedicalCenter newMedicalCenter) {

        List<String> validationErrors = validateMedicalCenter(newMedicalCenter, true);

        if (!validationErrors.isEmpty()) {
            throw new InvalidMedicalCenterException("Validación, campos vacios: " + String.join(", ", validationErrors));
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
            throw new InvalidMedicalCenterException("Tiene salas asociadas al centro médico");
        } else {
            medicalCenterRepository.deleteById(id);
        }

    }


    //PRIVATE METHODS
    private List<String> validateDevice(Device device, boolean update) {
        List<String> validate = new ArrayList<>();

        if (device.getDeviceId() == Integer.parseInt(null)) {
            validate.add("DeviceId");
        }
        if (device.getModel().isEmpty()) {
            validate.add("Model");
        }
        if (device.getInstallation() == null) {
            validate.add("Installation");
        }
        if (device.getRoom() == null) {
            validate.add("Room");
        }
        if (device.getRoomId() == Integer.parseInt(null)) {
            validate.add("Room id");
        }
        return validate;
    }

    private void associateMedicalCenterWithUser(MedicalCenter medicalCenter, User user) {
        medicalCenter.setUser(user);
        medicalCenterRepository.save(medicalCenter);
    }

    private boolean validateUserDevices(Device device) {

        //AQUI SE LE LA VARA
        return true;
    }

    @Override
    public List<Device> getAllDevicesByUserId(int id) {
        return null;
    }

    @Override
    public Device getDeviceById(int id) {
        return null;
    }

    @Override
    public Device saveDevice(Device device, int id) {
        return null;
    }

    @Override
    public Device updateDevice(int id, Device device) {
        return null;
    }

    @Override
    public void deleteDevice(int id) {

    }
}
