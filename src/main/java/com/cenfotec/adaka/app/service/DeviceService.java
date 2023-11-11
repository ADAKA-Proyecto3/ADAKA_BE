package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Device;
import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface DeviceService {

    List<Device> getAllDevices(int adminId);
    List<Device> getAllDevicesByRoom(int roomId);
    Device getDeviceById(int id);
    Device saveDevice(Device device,int admin);
    Device updateDevice(int id, Device device);
    void deleteDevice(int id);
}
