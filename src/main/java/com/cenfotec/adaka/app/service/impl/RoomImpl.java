package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.Room;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.RoomRepository;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository; // Create this repository interface

    @Override
    public List<Room> getAllRooms(int id) {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(int id) {
        return null;
    }

    @Override
    public Room saveRoom(Room room, int id) {
        return null;
    }

    @Override
    public Room updateRoom(int id, Room room) {
        return null;
    }

    @Override
    public void deleteRoom(int id) {

    }
}
