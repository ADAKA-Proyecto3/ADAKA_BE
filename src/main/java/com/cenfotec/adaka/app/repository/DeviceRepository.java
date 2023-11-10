package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    boolean existsDeviceByDeviceIdAndUserId (int DeviceId, int userId);
    boolean existsDeviceByDeviceIdAndRoomsIsNotEmpty(int id);

    List<Device> findByUserId(int id);
}


