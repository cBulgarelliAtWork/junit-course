package com.overit.junitcourse.example1;

/**
 * {@link MessageUtil} basic implementation.
 */
public class MessageUtilImpl implements MessageUtil {

    static final String HELLO = "Hello ";

    @Override
    public String getHelloMessage(String name) {
        return HELLO + name;
    }
}
