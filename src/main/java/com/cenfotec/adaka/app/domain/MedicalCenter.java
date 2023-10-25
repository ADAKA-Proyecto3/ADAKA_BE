package com.cenfotec.adaka.app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MEDICAL CENTER CLASS
 * This pojo contains all the information that is required to create a medical center inside the ZhenAir application
 * @Author Alberto Solano
 * @CreatedDate: October 6th, 2023
 * */
@Entity
@Data
@NoArgsConstructor
@Table(name = "medicalCenter")
public class MedicalCenter {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /**
     *Medical center name
     */
    String name;

    /**
     *Medical center direction
     */
    String direction;

    /**
     *Medical center latitude
     */
    String latitude;

    /**
     *Medical center longitude
     */
    String longitude;

    /**
     *Medical center email for notificatiions
     */
    String email;

    /**
     * Medical center status
     */
    @Enumerated(EnumType.STRING)
    Status status;

    /**
    * User reference
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    @JsonBackReference
    private User user;


    /**
     * List of rooms of medical center
     * */
    @OneToMany(mappedBy = "medicalCenter", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Room> rooms = new ArrayList<>();


}
