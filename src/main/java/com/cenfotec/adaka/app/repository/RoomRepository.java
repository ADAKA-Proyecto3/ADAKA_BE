package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Room> findAll();
    @Query("SELECT r AS room, mc.id AS medicalCenterId FROM Room r JOIN r.medicalCenter mc WHERE mc.user.id = ?1")
    List<Map<String, Object>> findAllRoomsByUserId(int userId);

    @Query("SELECT r AS room FROM Room r WHERE r.device.id = :deviceId ")
    Optional<Room> findByDevice(@Param("deviceId") int deviceId);
}
