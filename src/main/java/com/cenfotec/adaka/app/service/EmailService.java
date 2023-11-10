package com.cenfotec.adaka.app.service;

public interface EmailService {
   void  sendMessage(String to, String subject, String text);
}
