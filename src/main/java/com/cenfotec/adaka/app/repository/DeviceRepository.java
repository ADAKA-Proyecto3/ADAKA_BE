package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.Device;
import com.cenfotec.adaka.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
   List<Device>findAllByUser(User user);



}


