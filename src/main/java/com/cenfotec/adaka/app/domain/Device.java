package com.cenfotec.adaka.app.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "device")
public class Device {

        /**
         * Unique identifier
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        /**
         * Device identification
         */
        int deviceId;

        /**
         * Device model
         */
        String model;

        /**
         * Installation date set for the device
         */

        Date installation;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_room")
        @JsonBackReference
        private Room room;

        @Transient
        private int roomId;

}

