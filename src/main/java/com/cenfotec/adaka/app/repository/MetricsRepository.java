package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.Measure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MetricsRepository extends CrudRepository<Measure, Integer> {
    List<Measure> findAll();

    @Query("SELECT m FROM Measure m WHERE m.room.id = :roomId ORDER BY m.timestamp DESC")
    List<Measure> findTopByRoomIdOrderByDateDesc(@Param("roomId") int roomId, Pageable pageable);


    @Query("SELECT m FROM Measure m WHERE m.room.id = :roomId AND m.timestamp BETWEEN :startDate AND :endDate")
    List<Measure> findAllByRoomIdAndDateRange(@Param("roomId") int roomId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
