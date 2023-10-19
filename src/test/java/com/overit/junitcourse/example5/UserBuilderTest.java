package com.overit.junitcourse.example5;

import com.overit.junitcourse.example3.Gender;
import com.overit.junitcourse.example3.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class UserBuilderTest {

    private UserBuilder sut;
    private EasyRandom easyRandom;

    @BeforeEach
    public void setUp() {
        sut = new UserBuilder();
        easyRandom = new EasyRandom();
    }

    /**
     * static call to the real implementation of Gender.of(...)
     */
    @Test
    void gender_WrongGenderThrowsIllegalArgumentException_IllegalArgumentExceptionIsThrownFromRealGenderImplementation() {
        // given
        String genderAsString = easyRandom.nextObject(String.class);
        // when - then
        assertThatThrownBy(() -> sut.gender(genderAsString))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * static call to the mock implementation of Gender.of(...)
     */
    @Test
    void gender_WrongGenderThrowsIllegalArgumentException_IllegalArgumentExceptionIsThrownFromMockedGenderImplementation() {
        // given
        String genderAsString = Gender.MALE.toString();
        IllegalArgumentException expectedException = easyRandom.nextObject(IllegalArgumentException.class);
        try (MockedStatic<Gender> mockedGender = mockStatic(Gender.class)) {
            mockedGender.when(() -> Gender.of(genderAsString)).thenThrow(expectedException);
            // when - then
            assertThatThrownBy(() -> sut.gender(genderAsString))
                    .isInstanceOf(IllegalArgumentException.class)
                    .isEqualTo(expectedException);
            mockedGender.verify(() -> Gender.of(genderAsString));
        }
    }

    /**
     * static call to the mock implementation of Gender.of(...)
     */
    @Test
    void gender_RightGenderValue_UserIsReturned() {
        // given
        String name = easyRandom.nextObject(String.class);
        String surname = easyRandom.nextObject(String.class);
        LocalDate birthDate = easyRandom.nextObject(LocalDate.class);
        String email = easyRandom.nextObject(String.class);
        String genderAsString = easyRandom.nextObject(String.class);
        Gender gender = easyRandom.nextObject(Gender.class);
        User expected = new User(name, surname, birthDate, gender, email);
        sut.name(name).surname(surname).birthDate(birthDate).email(email);
        try (MockedStatic<Gender> mockedGender = mockStatic(Gender.class)) {
            mockedGender.when(() -> Gender.of(genderAsString)).thenReturn(gender);
            // when
            sut.gender(genderAsString);
            User actual = sut.build();
            // then
            assertThat(actual).isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
            mockedGender.verify(() -> Gender.of(genderAsString));
        }
    }
}