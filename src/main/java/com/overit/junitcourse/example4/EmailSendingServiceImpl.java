package com.overit.junitcourse.example4;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link EmailSendingService} standard implementation.
 */
@SuppressWarnings("unused")
@Slf4j
public class EmailSendingServiceImpl implements EmailSendingService {

    @Override
    public boolean sendEmail(String senderEmail, String recipientEmail, String subject, String body) {
        log.debug("sendEmail(senderEmail={}, recipientEmail={}, subject={}, body={})", senderEmail, recipientEmail, subject, body);
        //TODO: work in progress...
        return true;
    }
}
