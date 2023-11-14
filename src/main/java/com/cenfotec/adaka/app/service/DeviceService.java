package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Device;
import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface DeviceService {

    List<Device> getAllDevices(int adminId);
    Device getDeviceById(int id);
    Device saveDevice(Device device,int admin);
    void deleteDevice(int id);
}
