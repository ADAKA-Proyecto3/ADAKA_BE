package com.cenfotec.adaka.app.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

        LocalDateTime  installation;

        /**
         * Room  reference
         * */
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_room")
        @JsonBackReference
        Room room;

        /**
         * User reference
         * */
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_user")
        @JsonBackReference
        private User user;

}
