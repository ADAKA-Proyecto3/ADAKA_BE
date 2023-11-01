package com.cenfotec.adaka.app.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

/**
 * USER CLASS
 * This pojo contains all the information that is required to create a Subscription inside the ZhenAir application, which would define the
 * avaialable resorces for a specific admin user to have
 *
 * @Author ksegura
 * @CreatedDate: October 5th, 2023
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "subscription")
public class Subscription {
    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sb_id;
    /**
     * Payed ammount per subscription
     */
    double paymentAmount;
    /**
     * Currency used for the payment
     */
    String paymentCurrency;
    /**
     * Unique identifier retrieved by paypal api
     */
    String paymentId;
    /**
     * Name for the subscription
     */
    String planName;
    /**
     * Text are for the addres (ToDo create json builder to populate)
     */
    String shippingAddress;
    /**
     * This  field to represent the one-to-many relationship between Subscription and User
     */
    @OneToMany(mappedBy = "subscription")
    List<User> users;

}
