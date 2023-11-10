package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.repository.DeviceRepository;
import com.cenfotec.adaka.app.repository.RoomRepository;

import com.cenfotec.adaka.app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {


    @Autowired
    private DeviceRepository deviceRepository; // Create this repository interface
    @Autowired
    private RoomRepository roomRepository; // Create this repository interface


    @Override
    public List<Device> getAllDevices(int adminId) {
        return deviceRepository.findDevicesByUser_Id(adminId);
    }

    @Override
    public List<Device> getAllDevicesByRoom(int roomId) {
        return deviceRepository.findByRoom_Id(roomId);
    }

    @Override
    public Device getDeviceById(int id) {
        return deviceRepository.findById(id).get();
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device updateDevice(int id, Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(int id) {
        deviceRepository.deleteById(id);
    }
}
