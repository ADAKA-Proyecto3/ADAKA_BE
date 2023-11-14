package com.cenfotec.adaka.app.dto;

import lombok.Data;

import java.sql.Blob;

@Data
public class AdminDto {

    int id;
    String name;
    String  role;
    String  status;
    String phone;
    String email;
    String password;
    double paymentAmount;
    String paymentCurrency;
    String paymentId;
    String planName;
    String  shippingAddress;
}
