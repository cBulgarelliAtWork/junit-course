package com.overit.junitcourse.example4;

import com.overit.junitcourse.example3.User;

import java.util.List;

/**
 * Service useful to send newsletters.
 */
public interface NewsletterSendingService {

    /**
     * Sends a newsletter to the provided {@code recipient} men.<br>
     * Returns {@code true} in case of success; {@code false} otherwise.
     *
     * @param recipients {@link List}&lt;{@link User}&gt; about the recipients.
     * @param subject    {@link String} about the e-mail subject.
     * @param body       {@link String} about the e-mail body.
     * @return {@code true} in case of success; {@code false} otherwise.
     * @throws NewsletterSendingException exception thrown in case of problem occurred while sending the newsletter.
     */
    boolean sendNewsletterToMen(List<User> recipients, String subject, String body) throws NewsletterSendingException;
}
