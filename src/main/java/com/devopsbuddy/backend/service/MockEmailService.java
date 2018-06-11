package com.devopsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 * Mock implementation of EmailService
 */
public class MockEmailService extends AbstractEmailService {

    //Default logger
    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage mailMessage) {
        LOG.info("Sending message");
        LOG.info(mailMessage.toString());
        LOG.info("Email has been sent");
    }
}
