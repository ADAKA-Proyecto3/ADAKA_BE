package com.cenfotec.adaka.app.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class SubUserData {
    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
     MedicalCenter  medicalCenter;
     User manager;
    /**
     * User reference
     * */
    @OneToOne(mappedBy = " subUserData")
    @JsonManagedReference
   User subUser;
}
