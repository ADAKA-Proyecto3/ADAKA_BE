package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Response;
import com.cenfotec.adaka.app.domain.Room;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.exception.InvalidMedicalCenterException;
import com.cenfotec.adaka.app.exception.InvalidRoomException;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.cenfotec.adaka.app.service.RoomService;
import com.cenfotec.adaka.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/room")// controller
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private RoomService roomService;
    private Logger log = LoggerFactory.getLogger(MedicalCenterController.class);


    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> getAllRooms(@PathVariable int id) {
        log.debug("get all rooms method  started");
        try {
            List<Room> rooms = roomService.getAllRooms(id);
            return ResponseEntity.ok(new Response<>("Éxito", rooms));

        } catch (InvalidRoomException ex) {
            // Manejo específico para la excepción InvalidMedicalCenterException
            log.error("Error al obtener las salas del centro medico: " + ex.getMessage(), ex);

            // Manejo general para otras excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> getRoomById(@PathVariable int id) {
        log.debug("getRoomById method started");
        try {
            Room room = roomService.getRoomById(id);
            return ResponseEntity.ok(new Response<>("Éxito", Collections.singletonList(room)));
        } catch (InvalidRoomException ex) {
            // Manejo específico para la excepción InvalidMedicalCenterException
            log.error("Error al obtener la sala por ID: " + ex.getMessage(), ex);

            if (ex.getMessage().contains("The ID does not exist:")) {
                // Manejo específico para el caso de "No existe el ID"
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Error, " + ex.getMessage(), null));
            } else {
                // Manejo general para otras excepciones
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
            }
        }
    }


    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> saveRoom(@RequestBody Room room, @PathVariable int id) {
        log.debug("saveRoom method  started");
        try {

            Room newRoom = roomService.saveRoom(room, id);

            return ResponseEntity.ok(new Response<>("Éxito", Collections.singletonList(newRoom)));
        } catch (InvalidRoomException ex) {

            log.error("Error al crear la sala: " + ex.getMessage(), ex.getCause());

            if (ex.getMessage().contains("Some fields are empty: ")) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>("Error, " + ex.getMessage(), null));

            } else {
                // Manejo general para otras excepciones
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
            }
        }
    }

    @PutMapping(value = "/changeRoom/{roomId}/{medicalCenterId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> updateRoom(@PathVariable int roomId, @PathVariable int medicalCenterId, @RequestBody Room room) {
        log.debug("update MedicalCenter method started");

        try {
           Room updatedRoom =  roomService.updateRoom(roomId,medicalCenterId, room);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } catch (InvalidRoomException ex) {
            // Manejo de la excepción específica InvalidMedicalCenterException
            log.error("Error al actualizar la sala: " + ex.getMessage(), ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response<?>> deleteMedicalCenter(@PathVariable int id) {
        log.debug("delete status MedicalCenter method  started");
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok(new Response<>("Éxito", null));
        } catch (InvalidRoomException ex) {
            // Manejo de la excepción específica InvalidMedicalCenterException
            log.error("Error al eliminar la sala: " + ex.getMessage(), ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("Error, " + ex.getMessage(), null));
        }
    }

}