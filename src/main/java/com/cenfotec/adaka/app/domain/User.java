package com.cenfotec.adaka.app.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * USER CLASS
 * This pojo contains all the information that is required to create an  user inside the ZhenAir application
 * An by user this mean the person or end consumer from this system
 * @Author ksegura
 * @CreatedDate: October 5th, 2023
 * */
@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
    public class User {
    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    /**
     *User's legal name
     */
    String name;
    /**
     *User's Role within the Helath Institution
     */
    @Enumerated(EnumType.STRING)
    Role role;
    /**
     * User's status
     */
    @Enumerated(EnumType.STRING)
    Status status;
    /**
     * Contact mobile phone number
     */
    String phone;
    /**
     * Email for notifications
     */
    String email;
    /**
     * String encrypted password
     */
    String password;

}
