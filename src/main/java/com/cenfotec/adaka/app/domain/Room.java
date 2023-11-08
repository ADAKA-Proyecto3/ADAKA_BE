package com.cenfotec.adaka.app.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "room")
public class Room {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /**
     *Medical room name
     */
    String name;

    /**
     *volume of the room
     * Volume (m³) = Length (m) × Width (m) × Height (m)
     */

    long volume;

    /**
     *lenght of the room
     */
    long length;

    /**
     *width of the room
     */
    long width;

    /**
     *height of the room
     */
    long height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_medicalCenter")
    @JsonBackReference
    private MedicalCenter medicalCenter;

    @Transient
    private int medicalCenterId;
}
