package com.overit.junitcourse.example4;

/**
 * Service useful to validate e-mails.
 */
public interface EmailValidator {

    /**
     * Validates the provided e-mail, throwing an {@link InvalidEmailException} in case it's not valid.
     *
     * @param email {@link String} about the e-mail to check.
     * @throws InvalidEmailException exception thrown in case of invalid email found.
     */
    void validateEmail(String email) throws InvalidEmailException;
}
