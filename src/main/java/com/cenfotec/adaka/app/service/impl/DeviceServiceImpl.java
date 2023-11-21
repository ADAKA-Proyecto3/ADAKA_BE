package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.exception.UserNotFoundException;
import com.cenfotec.adaka.app.repository.DeviceRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
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
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Device> getAllDevices(int adminId) {

        User u = userRepository.findById(adminId).get();
        if(u!=null && u.getRole().equals(Role.ROLE_ADMIN)){
            return  deviceRepository.findAllByUser(u);
        }else  throw new UserNotFoundException("No hay administradores con ese ID registrados en la BD");

    }


    @Override
    public Device getDeviceById(int id) {
        return deviceRepository.findById(id).get();
    }

    @Override
    public Device saveDevice(Device device, int admin) {
        User user  = userService.getUserById(admin);
       if( user!=null && user.getRole().equals(Role.ROLE_ADMIN)){
           device.setUser(user);
           device.setInstallation(LocalDateTime.now());
           return deviceRepository.save(device);
       }else throw new UserNotFoundException("No hay administradores con ese ID registrados en la BD");

    }


    @Override
    public void deleteDevice(int id) {
        deviceRepository.deleteById(id);
    }

}
