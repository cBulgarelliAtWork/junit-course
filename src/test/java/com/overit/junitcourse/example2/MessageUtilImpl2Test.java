package com.overit.junitcourse.example2;

import com.overit.junitcourse.example1.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.overit.junitcourse.example2.MessageUtilImpl2.NAME_CANNOT_BE_NULL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageUtilImpl2Test {

    private MessageUtil2 sut;

    @BeforeEach
    void setUp() {
        sut = new MessageUtilImpl2();
    }

    @Test
    @DisplayName("getHelloMessage: when invoked with a not null name, a formatted hello message is expected")
    void getHelloMessage_WithNotNullName_ReturnsExpectedHelloMessage() throws MessageException {
        // given
        String name = "JUnit world";
        // when
        String actual = sut.getHelloMessage(name);
        // then
        assertEquals(MessageUtil.HELLO + name, actual);
    }

    @Test
    @DisplayName("getHelloMessage: when invoked with a null name, a MessageException is expected - (assertions made with JUnit)")
    void getHelloMessage_JUnitWithNullName_MessageExceptionIsThrown() {
        // when - then
        assertThrows(MessageException.class, () -> sut.getHelloMessage(null), NAME_CANNOT_BE_NULL);
    }

    @Test
    @DisplayName("getHelloMessage: when invoked with a null name, a MessageException is expected - (assertions made with AssertJ)")
    void getHelloMessage_AssertJWithNullName_MessageExceptionIsThrown() {
        // when - then
        assertThatThrownBy(() -> sut.getHelloMessage(null))
                .isInstanceOf(MessageException.class)
                .hasMessage(NAME_CANNOT_BE_NULL);
    }

    @Test
    @DisplayName("getHelloMessage: when invoked with an empty name, a MessageException is expected - (assertions made with AssertJ)")
    void getHelloMessage_AssertJWithAnEmptyName_MessageExceptionIsThrown() {
        // when - then
        assertThatThrownBy(() -> sut.getHelloMessage(""))
                .isInstanceOf(MessageException.class)
                .hasMessage(NAME_CANNOT_BE_NULL);
    }
}