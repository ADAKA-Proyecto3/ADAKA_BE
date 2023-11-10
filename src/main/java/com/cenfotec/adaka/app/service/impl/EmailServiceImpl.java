package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    @Override
    public boolean sendMessage(String to, String subject, String text)  {
        boolean returned = false;
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true indicates HTML format
            emailSender.send(message);
            returned = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            returned = false;
        }finally {
            return returned;
        }
    }
}
