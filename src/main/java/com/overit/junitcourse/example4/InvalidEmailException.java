package com.overit.junitcourse.example4;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvalidEmailException extends RuntimeException {

    @SuppressWarnings("unused")
    public InvalidEmailException(String message) {
        super(message);
    }
}
