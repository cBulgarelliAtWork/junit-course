package com.overit.junitcourse.example1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageUtilImplTest {

    private MessageUtil sut;

    @BeforeEach
    void setUp() {
        sut = new MessageUtilImpl();
    }

    @Test
    @DisplayName("getHelloMessage: when invoked with a not null name, a formatted hello message is expected")
    void getHelloMessage_WithNotNullName_ReturnsExpectedHelloMessage() {
        // given
        String name = "JUnit world";
        // when
        String actual = sut.getHelloMessage(name);
        // then
        assertEquals(MessageUtilImpl.HELLO + name, actual, "strings are not equal");
    }

    @Test
//    @DisplayName("getHelloMessage: when invoked with a not null name, a formatted hello message is expected")
    void getHelloMessage_WithNotNullNameAndAssertJ_ReturnsExpectedHelloMessage() {
        // given
        String name = "JUnit world";
        // when
        String actual = sut.getHelloMessage(name);
        // then
        assertThat(actual).isNotNull().isEqualTo(MessageUtilImpl.HELLO + name);
    }
}