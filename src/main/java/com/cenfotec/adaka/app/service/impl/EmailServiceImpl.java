package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Value("${sender.address}")
    private String senderAddress;

    @Autowired
    private JavaMailSender mailSender;
    private Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendMessage(String to, String subject, String text ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);
        mailSender.send(message);
        log.info("Mail Sended !!!!");


    }


}


