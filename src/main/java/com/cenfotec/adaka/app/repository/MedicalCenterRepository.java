package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicalCenterRepository extends CrudRepository<MedicalCenter, Integer> {
    List<MedicalCenter> findAll();
}
