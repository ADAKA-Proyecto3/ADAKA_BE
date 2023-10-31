package com.cenfotec.adaka.app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
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
@Builder
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


    public MedicalCenter(int id, String name, String direction, String latitude, String longitude, String email, Status status, User user, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.status = status;
        this.user = user;
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
