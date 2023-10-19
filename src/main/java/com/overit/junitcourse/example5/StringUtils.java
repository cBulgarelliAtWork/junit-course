package com.overit.junitcourse.example5;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Static class with methods useful for modifying strings.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    static final String VALUE_CANNOT_BE_NULL_OR_EMPTY = "value cannot be null or empty";

    /**
     * Returns the provided {@code value} converted to lower case.
     *
     * @param value {@link String} to convert to lower case.
     * @return the provided {@code value} converted to lower case.
     */
    public static String toLowerCase(String value) {
        log.debug("toLower(value={})", value);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL_OR_EMPTY);
        }
        String result = value.toLowerCase();
        log.debug("result is {}", result);
        return result;
    }
}
