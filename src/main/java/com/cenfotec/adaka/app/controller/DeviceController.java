package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.Device;
import com.cenfotec.adaka.app.domain.Response;
import com.cenfotec.adaka.app.exception.UserNotFoundException;
import com.cenfotec.adaka.app.service.impl.DeviceServiceImpl;
import com.cenfotec.adaka.app.util.mapper.DeviceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

        @Autowired
        private DeviceServiceImpl deviceService;
        @Autowired
        private DeviceUtil dju;

    /**
     * Returns all the devices asociated with one Admin user
     * @param Id
     * @return
     */
        @GetMapping("/all/{Id}")
        public ResponseEntity<Response<?>> getAllDevices(@PathVariable int Id){
            try {
                List<Device> all =  deviceService.getAllDevices(Id);
                return ResponseEntity.ok(new Response<>("Éxito", all));
            } catch (UserNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("No se encontro el usuario administrador con el ID :"+Id,null));
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") int deviceId) {
            Device device = deviceService.getDeviceById(deviceId);
            return ResponseEntity.ok().body(device);
        }

        @PostMapping("/{adminId}/save")
        public  ResponseEntity<Device> createDevice(@RequestBody Device device,@PathVariable int adminId ) {
            Device d =  deviceService.saveDevice(device, adminId);
            return ResponseEntity.ok().body(d);
        }


        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteDevice(@PathVariable(value = "id") int deviceId) {
            deviceService.deleteDevice(deviceId);
            return ResponseEntity.ok().build();
        }

    }


