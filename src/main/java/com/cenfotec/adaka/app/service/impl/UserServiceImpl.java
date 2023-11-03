package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.Role;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.Subscription;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.repository.SubscriptionRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository; // Create this repository interface
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = this.userRepository.getUserByEmail(email);
        return userOptional.orElse(null);
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
    public String  deleteUser(int id) {
        String message ;

        Optional<User> o = userRepository.findById(id);

        if(o.isEmpty()){
            message= "The user identified by the ID"+id+"has been succefully deleted";;
        }else{
            message= "error while deleting the user";
        }

        return message;

        //if(userRepository.findById(id)==null){
          //  message= "The user identified by the ID"+id+"has been succefully deleted";;
        //}else message= "error while deleting the user";
        //return message;
    }

    public User saveAdmin(User user, Subscription subscription) {
        user.setRole(Role.ADMIN);
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Subscription sb = subscriptionRepository.save(subscription);
        user.setSubscription(sb);
        return userRepository.save(user);
    }

}
