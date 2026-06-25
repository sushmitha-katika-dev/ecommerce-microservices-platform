package com.ecommerce.notification_service.service.impl;

import com.ecommerce.notification_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("========================================");
        log.info("Sending Email to: {}", to);
        log.info("Subject: {}", subject);
        log.info("Body:\n{}", body);
        log.info("========================================");
    }
}
