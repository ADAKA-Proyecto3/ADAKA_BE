package com.cenfotec.adaka.app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subUser")
    @JsonBackReference
   User subUser;
}
