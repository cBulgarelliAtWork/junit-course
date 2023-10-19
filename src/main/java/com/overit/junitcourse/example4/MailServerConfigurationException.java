package com.overit.junitcourse.example4;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailServerConfigurationException extends RuntimeException {

    public MailServerConfigurationException(String message) {
        super(message);
    }
}
