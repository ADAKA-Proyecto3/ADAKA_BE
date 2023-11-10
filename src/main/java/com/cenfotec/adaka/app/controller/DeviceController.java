package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.Device;
import com.cenfotec.adaka.app.service.impl.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

        @Autowired
        private DeviceServiceImpl deviceService;

        @GetMapping("/all/{Id}")
        public List<Device> getAllDevices(@PathVariable int Id){
            return deviceService.getAllDevices(Id);
        }


        @GetMapping("/room/{roomId}")
        public List<Device> getAllDevicesByRoom(@PathVariable int roomId) {
            return deviceService.getAllDevicesByRoom(roomId);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") int deviceId) {
            Device device = deviceService.getDeviceById(deviceId);
            return ResponseEntity.ok().body(device);
        }

        @PostMapping
        public Device createDevice(@RequestBody Device device) {
            return deviceService.saveDevice(device);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Device> updateDevice(@PathVariable(value = "id") int deviceId, @RequestBody Device deviceDetails) {
            Device updatedDevice = deviceService.updateDevice(deviceId, deviceDetails);
            return ResponseEntity.ok().body(updatedDevice);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteDevice(@PathVariable(value = "id") int deviceId) {
            deviceService.deleteDevice(deviceId);
            return ResponseEntity.ok().build();
        }

    }


