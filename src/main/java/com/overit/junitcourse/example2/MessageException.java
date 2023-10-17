package com.overit.junitcourse.example2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageException extends RuntimeException {

    public MessageException(String message) {
        super(message);
    }
}
