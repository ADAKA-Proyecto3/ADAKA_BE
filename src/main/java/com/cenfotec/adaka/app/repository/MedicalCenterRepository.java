package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.dto.MedicalCenterDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalCenterRepository extends CrudRepository<MedicalCenter, Integer> {
    List<MedicalCenter> findByUserId(int userId);
    boolean existsMedicalCenterByNameAndUserId (String name, int userId);
    boolean existsMedicalCenterByIdAndRoomsIsNotEmpty(int id);
    @Query("SELECT New com.cenfotec.adaka.app.dto.MedicalCenterDTO(mc.id, mc.name,  mc.direction, mc.latitude, mc.longitude, '', '') FROM MedicalCenter mc WHERE mc.showPublic = 1")
    List<MedicalCenterDTO> findAllMedicalCenters();
}

