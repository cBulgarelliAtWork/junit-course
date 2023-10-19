package com.overit.junitcourse.example4;

/**
 * Service useful to send e-mails.
 */
public interface EmailSendingService {

    /**
     * Sends an email to the provided {@code recipientEmail}.<br>
     * Returns {@code true} in case of success; {@code false} otherwise.
     *
     * @param senderEmail    {@link String} about the sender e-mail.
     * @param recipientEmail {@link String} about the recipient e-mail.
     * @param subject        {@link String} about the e-mail subject.
     * @param body           {@link String} about the e-mail body.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean sendEmail(String senderEmail, String recipientEmail, String subject, String body);
}
