package com.overit.junitcourse.example4;

import com.overit.junitcourse.example3.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@link NewsletterSendingService} standard implementation.
 */
@Slf4j
@RequiredArgsConstructor
public class NewsletterSendingServiceImpl implements NewsletterSendingService {

    static final String SENDER_EMAIL = "newsletter@overit.ai";
    static final String EMPTY_USERS_LIST_HAS_BEEN_PROVIDED = "An empty users list has been provided";
    static final String NO_USERS_HAVE_BEEN_FOUND = "No users have been found";
    static final String INVALID_E_MAIL_FOUND = "Invalid e-mail found";
    @NonNull
    private final UserService userService;
    @NonNull
    private final EmailSendingService emailSendingService;
    @NonNull
    private final EmailValidator emailValidator;

    @Override
    public boolean sendNewsletterToMen(@NonNull List<User> recipients, @NonNull String subject, @NonNull String body) throws NewsletterSendingException {
        log.debug("sendNewsletterToMen(recipients={}, subject={}, body={})", recipients, subject, body);
        boolean[] result = {true};
        try {
            // I only keep men from the list provided
            List<User> menRecipients = userService.filterByGender(Gender.MALE, recipients);
            log.debug("validating {} users", menRecipients.size());
            menRecipients.forEach(recipient -> validateEmail(recipient.getEmail()));
            log.debug("sending the newsletter to {} users", menRecipients.size());
            menRecipients.forEach(recipient -> result[0] &= emailSendingService.sendEmail(SENDER_EMAIL, recipient.getEmail(), subject, body));
        } catch (EmptyUsersException ex) {
            throw new NewsletterSendingException(EMPTY_USERS_LIST_HAS_BEEN_PROVIDED, ex);
        } catch (NoUsersFoundException ex) {
            throw new NewsletterSendingException(NO_USERS_HAVE_BEEN_FOUND, ex);
        } catch (InvalidEmailException ex) {
            throw new NewsletterSendingException(INVALID_E_MAIL_FOUND, ex);
        }
        log.debug("result is {}", result[0]);
        return result[0];
    }

    /**
     * Validates the provided e-mail, throwing an {@link InvalidEmailException} in case it's not valid.<br>
     * <strong>IMPORTANT</strong>: This method is useful to work on {@code Mockito spies}.
     *
     * @param email {@link String} about the e-mail to check.
     */
    void validateEmail(String email) throws InvalidEmailException {
        log.debug("validateEmail(email={}", email);
        emailValidator.validateEmail(email);
    }
}
