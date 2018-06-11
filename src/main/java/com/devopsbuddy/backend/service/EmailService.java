package com.devopsbuddy.backend.service;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     * Sends an Email with the content in FeedbackPOJO
     * @param feedbackPojo POJO that data will be used to create email
     */
    void sendFeedbackEmail(FeedbackPojo feedbackPojo);

    /**
     * Sends yhe email using SimpleMailMessage
     * @param mailMessage Object containing email content
     */
    void sendGenericEmailMessage(SimpleMailMessage mailMessage);
}
