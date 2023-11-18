package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.Measure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface MetricsRepository extends CrudRepository<Measure, Integer> {
    List<Measure> findAll();

    @Query("SELECT m FROM Measure m ORDER BY m.timestamp DESC")
    List<Measure> findTopByRoomIdOrderByDateDesc(int roomId, Pageable pageable);

    @Query("SELECT m FROM Measure m")
    List<Measure> findAllByRoomIdAndDateRange(int roomId, LocalDate startDate, LocalDate endDate);
}
