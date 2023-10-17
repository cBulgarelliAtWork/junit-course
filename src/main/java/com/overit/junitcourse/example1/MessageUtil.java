package com.overit.junitcourse.example1;

/**
 * Service useful to create messages.
 */
public interface MessageUtil {

    String HELLO = "Hello ";

    /**
     * Returns a {@link String} about the hello message, using the provided {@code name}.
     *
     * @param name {@link String} about the name.
     * @return a {@link String} about the hello message, using the provided {@code name}.
     */
    String getHelloMessage(String name);
}
