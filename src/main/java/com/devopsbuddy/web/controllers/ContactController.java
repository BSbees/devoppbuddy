package com.devopsbuddy.web.controllers;

import com.devopsbuddy.backend.service.EmailService;
import com.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController{
    //Default logger
    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

    //Key of the FeedbackPojo object to identify in a model
    private static final String FEEDBACK_MODEL_KEY = "feedback";

    //Contact page URL
    private static final String CONTACT_US_VIEW_NAME = "contact/contact";

    //EmailService bean
    @Autowired
    private EmailService emailService;

    @GetMapping("/contact")
    public String contactGet(ModelMap model){
        FeedbackPojo feedbackPojo = new FeedbackPojo();
        model.addAttribute(FEEDBACK_MODEL_KEY, feedbackPojo);
        return CONTACT_US_VIEW_NAME;
    }

    @PostMapping("/contact")
    public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) FeedbackPojo feedbackPojo){
        LOG.info("FeedbackPOJO content: {}", feedbackPojo);
        emailService.sendFeedbackEmail(feedbackPojo);
        return CONTACT_US_VIEW_NAME;
    }
}
