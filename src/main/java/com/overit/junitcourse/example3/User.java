package com.overit.junitcourse.example3;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;

@Slf4j
//@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class User implements Serializable {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private String email;
}
