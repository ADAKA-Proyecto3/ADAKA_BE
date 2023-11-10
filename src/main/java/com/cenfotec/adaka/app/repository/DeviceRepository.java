package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device>findDevicesByUser_Id(int adminId);
    List<Device> findByRoom_Id(int id);


}


