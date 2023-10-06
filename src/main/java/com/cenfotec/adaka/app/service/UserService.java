package com.cenfotec.adaka.app.service;


import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface  UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(int id, User user);
    void deleteUser(int id);
}
