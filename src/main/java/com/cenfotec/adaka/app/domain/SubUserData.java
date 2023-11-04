package com.cenfotec.adaka.app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@Entity
public class SubUserData {
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
