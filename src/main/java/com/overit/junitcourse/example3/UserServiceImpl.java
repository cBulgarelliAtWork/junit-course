package com.overit.junitcourse.example3;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {

    static final String EMPTY_USERS_LIST_HAS_BEEN_PROVIDED = "Empty users list has been provided";
    static final String NO_USERS_WERE_FOUND_BY_APPLYING_THE_REQUESTED_FILTER = "No users were found by applying the requested filter";

    @Override
    public List<User> filterByGender(@NonNull Gender gender, @NonNull List<User> users) throws EmptyUsersException, NoUsersFoundException {
        log.debug("filterByGender(gender={}, users={})", gender, users);
        if (users.isEmpty()) {
            throw new EmptyUsersException(EMPTY_USERS_LIST_HAS_BEEN_PROVIDED);
        }
        List<User> result = users.stream().filter(u -> gender.equals(u.getGender())).toList();
        if (result.isEmpty()) {
            throw new NoUsersFoundException(NO_USERS_WERE_FOUND_BY_APPLYING_THE_REQUESTED_FILTER);
        }
        log.debug("result is {}", result);
        return result;
    }
}
