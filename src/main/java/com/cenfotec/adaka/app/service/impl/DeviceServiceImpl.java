package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.repository.DeviceRepository;
import com.cenfotec.adaka.app.repository.RoomRepository;

import com.cenfotec.adaka.app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {


    @Autowired
    private DeviceRepository deviceRepository; // Create this repository interface
    @Autowired
    private RoomRepository roomRepository; // Create this repository interface
    @Autowired
    private RoomImpl roomservice;
    @Autowired
    private UserServiceImpl userService;


    @Override
    public List<Device> getAllDevices(int adminId) {
        return deviceRepository.findDevicesByUser_Id(adminId);
    }

    @Override
    public List<Device> getAllDevicesByRoom(int roomId) {
        return deviceRepository.findByRoomId(roomId);
    }

    @Override
    public Device getDeviceById(int id) {
        return deviceRepository.findById(id).get();
    }

    @Override
    public Device saveDevice(Device device, int admin) {
        int roomId = device.getRoomId();
        Room room = roomservice.getRoomById(roomId);
        User user  = userService.getUserById(admin);
       if(room!=null && user!=null && user.getRole().equals(Role.ADMIN)){
           device.setUser(user);
           device.setInstallation(LocalDateTime.now());
           room.setDeviceId(device.getId());
           roomservice.updateRoom(room.getId(),room.getMedicalCenter().getId(),room);///add the device id to the room
           return deviceRepository.save(device);
       }else throw new IllegalArgumentException("si la sala es agregada al device, la misma debe de existir previamente en la bd ");


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
