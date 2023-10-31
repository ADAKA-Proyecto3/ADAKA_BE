package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms(int id);
    Room getRoomById(int id);
    Room saveRoom(Room room, int id);
    void updateRoom(int id, Room room);
    void deleteRoom(int id);

}
