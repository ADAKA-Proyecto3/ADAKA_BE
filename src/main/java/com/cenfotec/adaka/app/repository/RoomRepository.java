package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Room;
import com.cenfotec.adaka.app.dto.RoomDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Room> findAll();
    @Query("SELECT NEW com.cenfotec.adaka.app.dto.RoomDTO(r, mc.id) FROM Room r JOIN r.medicalCenter mc WHERE mc.user.id = ?1")
    List<RoomDTO> findAllByUserId(int userId);
}
