package com.cenfotec.adaka.app.service;


import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface  UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User saveUser(User user);
    User updateUser(int id, User user);
    String  deleteUser(int id);
}
