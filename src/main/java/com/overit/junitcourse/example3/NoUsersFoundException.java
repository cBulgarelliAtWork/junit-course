package com.overit.junitcourse.example3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NoUsersFoundException extends RuntimeException {

    public NoUsersFoundException(String message) {
        super(message);
    }
}
