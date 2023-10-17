package com.overit.junitcourse.example3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmptyUsersException extends RuntimeException {

    public EmptyUsersException(String message) {
        super(message);
    }
}
