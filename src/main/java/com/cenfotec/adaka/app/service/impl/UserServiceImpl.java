package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.Role;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.Subscription;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.repository.SubscriptionRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository; // Create this repository interface
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {

        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public User  updateUser(int id, User user) {
        user.setId(id);
       return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User saveAdmin(User user, Subscription subscription) {
        user.setRole(Role.ADMIN);
        user.setStatus(Status.ACTIVE);
        Subscription sb = subscriptionRepository.save(subscription);
        user.setSubscription(sb);
        return userRepository.save(user);
    }

}
