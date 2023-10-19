package com.overit.junitcourse.example4;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsletterSendingException extends RuntimeException {

    @SuppressWarnings("unused")
    public NewsletterSendingException(String message) {
        super(message);
    }

    public NewsletterSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
