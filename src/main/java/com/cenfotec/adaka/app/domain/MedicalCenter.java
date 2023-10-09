package com.cenfotec.adaka.app.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
     *Medical center coordinates Lon - Lat
     */
    String coordinates;

    /**
     *Medical center email for notificatiions
     */
    String email;

    /**
     * Medical center status
     */
    @Enumerated(EnumType.STRING)
    Status status;
}
