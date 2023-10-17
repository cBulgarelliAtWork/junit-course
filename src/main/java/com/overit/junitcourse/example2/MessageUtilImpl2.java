package com.overit.junitcourse.example2;

import com.overit.junitcourse.example1.MessageUtil;

/**
 * {@link MessageUtil} basic implementation managing exceptions.
 */
public class MessageUtilImpl2 implements MessageUtil2 {

    static final String NAME_CANNOT_BE_NULL = "name cannot be null";

    @Override
    public String getHelloMessage(String name) throws MessageException {
        if (name == null || name.trim().isEmpty()) {
            throw new MessageException(NAME_CANNOT_BE_NULL);
        }
        return HELLO + name;
    }
}
