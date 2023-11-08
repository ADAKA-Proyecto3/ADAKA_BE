package com.cenfotec.adaka.app.dto;

import com.cenfotec.adaka.app.domain.Room;

public class RoomDTO {
    private Room room;
    private int medicalCenterId;

    public RoomDTO(Room room, int medicalCenterId) {
        this.room = room;
        this.medicalCenterId = medicalCenterId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getMedicalCenterId() {
        return medicalCenterId;
    }

    public void setMedicalCenterId(int medicalCenterId) {
        this.medicalCenterId = medicalCenterId;
    }
}
