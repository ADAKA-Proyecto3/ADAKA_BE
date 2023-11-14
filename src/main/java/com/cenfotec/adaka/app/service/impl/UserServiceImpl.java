package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.exception.UserNotFoundException;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.SubscriptionRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.EmailService;
import com.cenfotec.adaka.app.service.UserService;
import com.cenfotec.adaka.app.util.user.PasswordGeneratorUtil;
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

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    private PasswordGeneratorUtil passwordGeneratorUtil;

    @Autowired
    private MedicalCenterRepository medicalCenterRepository; // Create this repository interface

    @Override
    public List<User> getAllUsers(int manager) {
        return userRepository.findUsersByManager(manager);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

//    @Override
//    public User getUserByEmail(String email) {
//        Optional<User> userOptional = this.userRepository.getUserByEmail(email);
//        return userOptional.orElse(null);
//    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = this.userRepository.getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!user.getRole().equals("ADMIN")) {
                Optional<MedicalCenter> medicalCenterOptional = medicalCenterRepository.findById(user.getAssignedMedicalCenter());
                if (medicalCenterOptional.isPresent()) {
                    MedicalCenter medicalCenter = medicalCenterOptional.get();
                    List<MedicalCenter> userMedicalCenters = user.getMedicalCenters();
                    userMedicalCenters.add(medicalCenter);
                    user.setMedicalCenters(userMedicalCenters);
                }
            }
        }

        return userOptional.orElse(null);
    }


    @Override
    public User saveUser(User user, int parentId, int medicalCenterId) {
        User admin = userRepository.findById(parentId).get();
        MedicalCenter medicalCenter =  medicalCenterRepository.findById(medicalCenterId).get();
        User temp = null;
        String password = generatePassword();
        if(parentId>=1 && medicalCenterId>=1 & admin!=null & medicalCenter!=null){
            user.setManager(parentId);
            user.setAssignedMedicalCenter(medicalCenterId);
            user.setPassword(passwordEncoder.encode(password));
            user.setStatus(Status.FREEZE);
            temp = user;
            if(userRepository.save(user)!=null){
                emailService.sendMessage(temp,password);
            }
        }

        return temp;
    }

    @Override
    public User resetUserPassword(String email) {
        User temp ;
        String password =generatePassword();
         temp = getUserByEmail(email);
         if(temp!=null){
             temp.setPassword(passwordEncoder.encode(password));
             temp.setStatus(Status.FREEZE);
             if( userRepository.save(temp)!=null){
                 emailService.sendMessage(temp,password);
             }
             return temp;
         }else {

             throw new UserNotFoundException("No se encontro ningun usuario con el correo indicado");
         }

    }

    private String generatePassword() {
        return passwordGeneratorUtil.generatePassword();
    }

    @Override
    public User  updateUser(int id, User user) {
        User oldUser = getUserById(id);
        oldUser.setEmail(user.getEmail());
        oldUser.setPhone(user.getPhone());
        oldUser.setName(user.getName());
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(oldUser);
    }

    @Override
    public User updatePasswordUser(int id, User user) {
        User oldUser = getUserById(id);
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setStatus(Status.ACTIVE);
       return userRepository.save(oldUser);
    }

    @Override
    public String  deleteUser(int id) {
        String message ;
        Optional<User> o = userRepository.findById(id);
        if(o.isPresent()){
            userRepository.deleteById(o.get().getId());
            message= "The user identified by the ID"+id+"has been successfully deleted";;
        }else{
            message= "error while deleting the user";
        }
        return message;

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
