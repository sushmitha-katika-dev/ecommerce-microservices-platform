package com.ecommerce.notification_service.service.impl;

import com.ecommerce.notification_service.controller.*;
import com.ecommerce.notification_service.entity.*;
import com.ecommerce.notification_service.repository.*;
import com.ecommerce.notification_service.kafka.consumer.*;
import com.ecommerce.notification_service.kafka.event.*;
import com.ecommerce.notification_service.service.impl.*;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    public void sendEmail(String to, String subject, String body) {
        log.info("========================================");
        log.info("Sending Email to: {}", to);
        log.info("Subject: {}", subject);
        log.info("Body:\n{}", body);
        log.info("========================================");
    }
}

