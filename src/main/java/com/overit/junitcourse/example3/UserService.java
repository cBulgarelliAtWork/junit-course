package com.overit.junitcourse.example3;

import java.util.List;

public interface UserService {

    List<User> filterByGender(Gender gender, List<User> users) throws EmptyUsersException, NoUsersFoundException;
}
