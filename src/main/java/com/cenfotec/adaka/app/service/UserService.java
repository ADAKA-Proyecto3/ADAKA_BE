package com.cenfotec.adaka.app.service;


import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface  UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByEmail(String email);
    User saveUser(User user, int parentId, int medicalCenterId);
    User updateUser(int id, User user);
    String  deleteUser(int id);
}
