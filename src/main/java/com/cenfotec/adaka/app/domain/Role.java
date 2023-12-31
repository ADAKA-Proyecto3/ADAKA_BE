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
    ROLE_NURSE,
    ROLE_MEDICAL_DOCTOR,
    ROLE_IT_SPECIALIST,
    ROLE_OTHER
}
