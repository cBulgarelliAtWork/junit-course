package com.overit.junitcourse.example3;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
@Slf4j
public enum Gender {

    MALE("M"), FEMALE("F");
    static final String UNEXPECTED_OPERATOR_VALUE = "Unexpected operator value: %s";
    private final String code;

    @SuppressWarnings("unused")
    public static Gender of(@NonNull String code) {
        log.debug("of(code={})", code);
        Gender result = Arrays.stream(Gender.values())
                .filter(o -> o.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format(UNEXPECTED_OPERATOR_VALUE, code)));

        log.debug("result is {}", result);
        return result;
    }
}