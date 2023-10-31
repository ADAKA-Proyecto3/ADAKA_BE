package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Room;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.exception.InvalidRoomException;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.RoomRepository;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository; // Create this repository interface
    @Autowired
    private MedicalCenterService medicalCenterService;

    @Override
    public List<Room> getAllRooms(int id) {
        MedicalCenter medicalCenter = medicalCenterService.getMedicalCenterById(id);
        List<Room> rooms = medicalCenter.getRooms();

        return rooms;
    }

    @Override
    public Room getRoomById(int id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            return optionalRoom.get();
        } else {
            throw new InvalidRoomException("The ID does not exist: " + id);
        }
    }

    @Override
    public Room saveRoom(Room room, int id) {
        MedicalCenter medicalCenter = medicalCenterService.getMedicalCenterById(id);
        // Realizar validación detallada
        List<String> validationErrors = validateRooms(room);

        if (!validationErrors.isEmpty()) {
            throw new InvalidRoomException("Validation errors: " + String.join(", ", validationErrors));
        }
        if (!validateMedicalCenterRoom(medicalCenter)) {
            throw new InvalidMedicalCenterException("Maximum number of rooms according to the user's plan");
        }
        long newVolume = room.getLength() * room.getWidth() * room.getHeight();
        room.setVolume(newVolume);
        // Guardar el centro médico en la base de datos
        Room newRoom = roomRepository.save(room);

        // Asociar la sala al centro médico
        associateRoomWithMedicalCenter(medicalCenter, newRoom);

        return newRoom;
    }

    @Override
    public void updateRoom(int id, Room newRoom) {
        List<String> validationErrors = validateRooms(newRoom);

        if (!validationErrors.isEmpty()) {
            throw new InvalidRoomException("Validation errors: " + String.join(", ", validationErrors));
        }
        //Get the old room and update the relational values
        Room oldRoom = getRoomById(id);
        oldRoom.setName(newRoom.getName());
        oldRoom.setHeight(newRoom.getHeight());
        oldRoom.setLength(newRoom.getLength());
        oldRoom.setWidth(newRoom.getWidth());
        long newVolume = newRoom.getLength() * newRoom.getWidth() * newRoom.getHeight();
        oldRoom.setVolume(newVolume);

        roomRepository.save(oldRoom);
    }

    @Override
    public void deleteRoom(int id) {
        try {
            Room room = getRoomById(id);
            roomRepository.delete(room);

        } catch (Exception ex) {
            throw new InvalidMedicalCenterException("Error deleting room", ex);
        }
    }

    //PRIVATE
    private List<String> validateRooms(Room room) {
        List<String> validate = new ArrayList<>();
        Long length = room.getLength();
        Long width = room.getWidth();
        Long height = room.getHeight();

        if (length == null) {
            validate.add("Length");
        }
        if (width == null) {
            validate.add("Width");
        }
        if (height == null) {
            validate.add("Height");
        }
        if (room.getName().isEmpty()) {
            validate.add("Name");
        }

        return validate;
    }

    private void associateRoomWithMedicalCenter(MedicalCenter medicalCenter, Room room) {
        room.setMedicalCenter(medicalCenter);
        roomRepository.save(room);
    }

    private boolean validateMedicalCenterRoom(MedicalCenter medicalCenter) {
        List<Room> rooms = medicalCenter.getRooms();
        //AQUI SE LE LA VARA
        return true;
    }
}
