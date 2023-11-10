package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Device;
import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface DeviceService {

    List<Device> getAllDevcesByUserId(int id);

    List<Device> getAllDevicesByUserId(int id);
    Device getDeviceById(int id);
    Device saveDevice(Device device, int id);
    Device updateDevice(int id, Device device);
    void deleteDevice(int id);
}
