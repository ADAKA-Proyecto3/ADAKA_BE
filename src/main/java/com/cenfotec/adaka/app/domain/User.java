package com.cenfotec.adaka.app.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Builder
@AllArgsConstructor
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
     *User's Role within the Health Institution
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

    /**
     * List of medical centers per  user
     * */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<MedicalCenter> medicalCenters = new ArrayList<>();

    /**
     * List of medical centers per  user
     * */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Device> devices = new ArrayList<>();

    /**
     * Subscription for the Admin user otherwise empty.
     */
    @ManyToOne
    @JoinColumn(name = "sb_id")
    Subscription subscription;
    /**
     * The subuser's manager ID (aka admin)
     */
   int manager;
    /**
     * The subuser's medical's  ID (aka medical center)
     */
   int assignedMedicalCenter;
}
