package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalCenterRepository extends CrudRepository<MedicalCenter, Integer> {
    List<MedicalCenter> findAll();
}
