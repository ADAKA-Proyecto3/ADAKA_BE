package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.exception.InvalidRoomException;
import com.cenfotec.adaka.app.repository.RoomRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.RoomService;
import com.cenfotec.adaka.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoomImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository; // Create this repository interface
    @Autowired
    private MedicalCenterService medicalCenterService;

    @Autowired
    private UserService userService;
    @Autowired
    private DeviceServiceImpl deviceService;

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
    // Listar salas con id usuario

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
        newRoom.setMedicalCenterId(id);
   
        return newRoom;
    }

    @Override
    public Room updateRoom(int roomId, int medicalCenterId, Room newRoom) {

        Optional<MedicalCenter> medicalCenter = Optional.ofNullable(medicalCenterService.getMedicalCenterById(medicalCenterId));
        Optional<Room> room = Optional.ofNullable(getRoomById(roomId));

        if (medicalCenter.isPresent() && room.isPresent()) {
            Room db_room = room.get();
            db_room.setName(newRoom.getName());
            db_room.setHeight(newRoom.getHeight());
            db_room.setWidth(newRoom.getWidth());
            db_room.setLength(newRoom.getLength());
            long volume = newRoom.getLength() * newRoom.getWidth() * newRoom.getHeight();
            db_room.setVolume(volume);
            db_room.setMedicalCenter(medicalCenter.get());

            return roomRepository.save(db_room);
        } else {
            throw new InvalidRoomException("Validation errors: Error Updating Rooms");
        }
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


    @Override
    public List<Room> getAllRoomsByUserId(int id) {

        List<Room> rooms = new ArrayList<>();
        List<Map<String, Object>> results = roomRepository.findAllRoomsByUserId(id);
        User user = userService.getUserById(id);

        if (user.getRole().name().equals(Role.ROLE_ADMIN.toString())) {
            for (Map<String, Object> result : results) {
                Room room = (Room) result.get("room");
                Integer medicalCenterId = (Integer) result.get("medicalCenterId");

                // Asigna el ID del MedicalCenter al Room
                room.setMedicalCenterId(medicalCenterId);
                rooms.add(room);
            }
        } else {
            MedicalCenter medicalCenter = medicalCenterService.getMedicalCenterById(user.getAssignedMedicalCenter());
            List<Room> ListRoom = medicalCenter.getRooms();
            for (Room roomElement : ListRoom) {
                Room room = roomElement;
                // Asigna el ID del MedicalCenter al Room
                room.setMedicalCenterId(medicalCenter.getId());
                rooms.add(room);
            }
        }

        return rooms;
    }

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
        return true;
    }

    @Override
    public Room updateAddDeviceToRoom(int roomId, int deviceId) {

        Device d = deviceService.getDeviceById(deviceId);
        Optional<Room> room = Optional.ofNullable(getRoomById(roomId));
        if (d == null) {
            throw new InvalidRoomException("Validation errors: Error Updating Rooms, device not found");
        }
        if (!room.isPresent()) {
            throw new InvalidRoomException("Validation errors: Error Updating Rooms, device not found");
        }
        Room dbRoom = room.get();
        if(dbRoom.getDevice()!=null){
            int idOld = dbRoom.getDevice().getId();
            if (idOld == deviceId) {
                throw new InvalidRoomException("Validation errors: Error Updating Rooms, the room already have a device, only one device per room is allowed");
            }
        }
        dbRoom.setDevice(d);
        return roomRepository.save(dbRoom);
    }

}
