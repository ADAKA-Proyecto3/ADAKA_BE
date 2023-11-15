package com.cenfotec.adaka.app.domain;

/**
 * This class represents the Role 
 * Role: definition word  for user permissions based on key which in this case is the workers role within their position inside the Health Institutions, 
 * accordingly to their legal responsibilities 
 * @Author ksegura
 * @CreatedDate: October 5th, 2023
 * */
public enum Role {
    ROLE_ADMIN,
    NURSE,
    MEDICAL_DOCTOR,
    IT_SPECIALIST,
    OTHER
}
