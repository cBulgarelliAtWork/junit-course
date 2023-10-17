package com.overit.junitcourse.example3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.overit.junitcourse.example3.UserServiceImpl.EMPTY_USERS_LIST_HAS_BEEN_PROVIDED;
import static com.overit.junitcourse.example3.UserServiceImpl.NO_USERS_WERE_FOUND_BY_APPLYING_THE_REQUESTED_FILTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceImplTest {

    private UserService sut;

    @BeforeEach
    void setUp() {
        sut = new UserServiceImpl();
    }

    @Test
    void filterByGender_EmptyUsers_EmptyUsersExceptionIsThrown() {
        // given
        Gender gender = Gender.MALE;
        List<User> users = Collections.emptyList();
        // when - then
        assertThatThrownBy(() -> sut.filterByGender(gender, users))
                .isInstanceOf(EmptyUsersException.class)
                .hasMessage(EMPTY_USERS_LIST_HAS_BEEN_PROVIDED);
    }

    @Test
    void filterByGender_NoFilteredUsers_NoUsersFoundExceptionIsThrown() {
        // given
        Gender gender = Gender.FEMALE;
        List<User> users = List.of(User.builder()
                        .surname("Rossi")
                        .name("Mario")
                        .birthDate(LocalDate.of(1978, 1, 16))
                        .gender(Gender.MALE)
                        .build(),
                User.builder()
                        .surname("Verdi")
                        .name("Paolo")
                        .birthDate(LocalDate.of(1986, 10, 7))
                        .gender(Gender.MALE)
                        .build(),
                User.builder()
                        .surname("Bianchi")
                        .name("Carlo")
                        .birthDate(LocalDate.of(2000, 1, 31))
                        .gender(Gender.MALE)
                        .build());
        // when - then
        assertThatThrownBy(() -> sut.filterByGender(gender, users))
                .isInstanceOf(NoUsersFoundException.class)
                .hasMessage(NO_USERS_WERE_FOUND_BY_APPLYING_THE_REQUESTED_FILTER);
    }

    @Test
    void filterByGender_NotEmptyUsers_FilteredUsersAreReturned() {
        // given
        Gender gender = Gender.MALE;
        User marioRossi = User.builder()
                .surname("Rossi")
                .name("Mario")
                .birthDate(LocalDate.of(1978, 1, 16))
                .gender(Gender.MALE)
                .build();
        User paolaVerdi = User.builder()
                .surname("Verdi")
                .name("Paola")
                .birthDate(LocalDate.of(1986, 10, 7))
                .gender(Gender.FEMALE)
                .build();
        User carloBianchi = User.builder()
                .surname("Bianchi")
                .name("Carlo")
                .birthDate(LocalDate.of(2000, 1, 31))
                .gender(Gender.MALE)
                .build();
        // this is necessary to compulsorily use the recursive
        User expectedMarioRossi = User.builder()
                .surname("Rossi")
                .name("Mario")
                .birthDate(LocalDate.of(1978, 1, 16))
                .gender(Gender.MALE)
                .build();
        User expectedCarloBianchi = User.builder()
                .surname("Bianchi")
                .name("Carlo")
                .birthDate(LocalDate.of(2000, 1, 31))
                .gender(Gender.MALE)
                .build();
        List<User> users = List.of(marioRossi, paolaVerdi, carloBianchi);
        // when
        List<User> actual = sut.filterByGender(gender, users);
        // then
        assertThat(actual).isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator() // recursive field by field comparison strategy
                .containsExactlyInAnyOrder(expectedMarioRossi, expectedCarloBianchi);
    }
}