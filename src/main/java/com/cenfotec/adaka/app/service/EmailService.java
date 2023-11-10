package com.cenfotec.adaka.app.service;

public interface EmailService {
    public boolean sendMessage(String to, String subject, String text);
}
