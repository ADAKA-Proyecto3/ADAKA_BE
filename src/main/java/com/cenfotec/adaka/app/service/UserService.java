package com.cenfotec.adaka.app.service;


import com.cenfotec.adaka.app.domain.User;

import java.util.List;

public interface  UserService {
    List<User> getAllUsers(int manager);
    User getUserById(int id);
    User getUserByEmail(String email);
    User saveUser(User user, int parentId, int medicalCenterId);
    User resetUserPassword(String email);
    User updateUser(int id, User user);
    User updateSubUser(int id, User user);
    String  deleteUser(int id);
    User updatePasswordUser(int id, User user);
}
