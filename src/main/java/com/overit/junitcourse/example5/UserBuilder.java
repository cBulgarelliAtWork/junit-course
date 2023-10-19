package com.overit.junitcourse.example5;

import com.overit.junitcourse.example3.Gender;
import com.overit.junitcourse.example3.User;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

@NoArgsConstructor
public class UserBuilder {

    private static final Logger log = LogManager.getLogger(UserBuilder.class);
    static final String DECODED_GENDER_IS = "decoded gender is {}";
    static final String RETURN_IS = "return is {}";
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private String email;

    public UserBuilder name(String name) {
        log.debug("name(name={})", name);
        this.name = name;
        return this;
    }

    public UserBuilder surname(String surname) {
        log.debug("surname(surname={})", surname);
        this.surname = surname;
        return this;
    }

    public UserBuilder birthDate(LocalDate birthDate) {
        log.debug("birthDate(birthDate={})", birthDate);
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder gender(String genderAsString) {
        log.debug("gender(genderAsString={})", genderAsString);
        this.gender = Gender.of(genderAsString);
        log.debug(DECODED_GENDER_IS, gender);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public UserBuilder email(String email) {
        log.debug("email(email={})", email);
        this.email = email;
        return this;
    }

    public User build() {
        log.debug("build()");
        User result = new User(name, surname, birthDate, gender, email);
        log.debug(RETURN_IS, result);
        return result;
    }
}
