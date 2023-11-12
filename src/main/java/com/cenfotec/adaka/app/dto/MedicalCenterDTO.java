package com.cenfotec.adaka.app.dto;

import lombok.Data;

@Data
public class MedicalCenterDTO {
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
     *Medical center air status
     */
    String status;


    public MedicalCenterDTO(String name, String direction, String latitude, String longitude, String status) {
        this.name = name;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }
}
