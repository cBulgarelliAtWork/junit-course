package com.overit.junitcourse.example5;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.overit.junitcourse.example5.StringUtils.VALUE_CANNOT_BE_NULL_OR_EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringUtilsTest {

    private EasyRandom easyRandom;

    @BeforeEach
    public void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void toLowerCase_NullValue_IllegalArgumentExceptionIsThrown() {
        // when - then
        assertThatThrownBy(() -> StringUtils.toLowerCase(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(VALUE_CANNOT_BE_NULL_OR_EMPTY);
    }

    @Test
    void toLowerCase_EmptyValue_IllegalArgumentExceptionIsThrown() {
        // when - then
        assertThatThrownBy(() -> StringUtils.toLowerCase(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(VALUE_CANNOT_BE_NULL_OR_EMPTY);
    }

    @Test
    void toLowerCase_NotEmptyValue_LowerCaseValueIsReturned() {
        // given
        String value = easyRandom.nextObject(String.class);
        // when
        String actual = StringUtils.toLowerCase(value);
        // then
        assertThat(actual).isNotNull().isEqualTo(value.toLowerCase());
    }
}