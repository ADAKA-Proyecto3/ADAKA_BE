package com.cenfotec.adaka.app.service.impl;

import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.service.EmailService;
import com.cenfotec.adaka.app.util.notification.HtmlToStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${sender.address}")
    private String senderAddress;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    HtmlToStringUtil htmlToStringUtil;
    private Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendMessage(User user,String password ) {
        String htmlTextBody = htmlToStringUtil.createEmailBodyForUser(user,password);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderAddress);
            helper.setTo(user.getEmail());
            helper.setSubject(htmlToStringUtil.subject);
            helper.setText(htmlTextBody, true);  // set to true to enable HTML formatting
            mailSender.send(message);
            log.info("Mail Send...");
        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
    }


}


