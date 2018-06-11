package com.devopsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Real implementation of EmailService
 */
public class SmtpEmailService extends AbstractEmailService {

    //Default logger
    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage mailMessage) {
        LOG.info("Sending email for {}", mailMessage);
        mailSender.send(mailMessage);
        LOG.info("Email has been sent");
    }
}
