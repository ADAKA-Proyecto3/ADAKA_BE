package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> getUserByEmail(String email);

    List<User> findUsersByManager(int managerId);
    List<User> findUsersByAssignedMedicalCenter(int medicalCenterId);

}
