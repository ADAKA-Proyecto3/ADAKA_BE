package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.dto.EmailDto;
import com.cenfotec.adaka.app.exception.UserNotFoundException;
import com.cenfotec.adaka.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")// controller
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("all/{managerId}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int managerId) {
        log.debug("get all ser method  started");
        List<User> users = userService.getAllUsers(managerId);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        log.debug("getUserById method  started");
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.debug("getUserByEmail method  started");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{parentId}/{medicalCenterId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody User user, @PathVariable int parentId, @PathVariable int medicalCenterId) {
        log.debug("saveUser method  started");
        User savedUser = userService.saveUser(user, parentId, medicalCenterId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateSubUser(@PathVariable int id, @RequestBody User user) {
        log.debug("update User method  started");
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
           User modUser = userService.updateSubUser(id, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(modUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value = "/{id}/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        log.debug("update User method  started");
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            User modUser = userService.updateUser(id, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(modUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/password/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updatePasswordUser(@PathVariable int id, @RequestBody User user) {
        log.debug("update User method  started");
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            User modUser = userService.updatePasswordUser(id, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(modUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {

        Optional<User> o = Optional.ofNullable(userService.getUserById(id));

        if (o.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/recover",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody EmailDto emailDto) {
        log.debug("saveUser method  started");
        try {
            userService.resetUserPassword(emailDto.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(emailDto);
        } catch (UserNotFoundException unfe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(unfe.getMessage());

        }
    }
}
