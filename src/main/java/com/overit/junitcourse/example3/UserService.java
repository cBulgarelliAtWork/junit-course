package com.overit.junitcourse.example3;

import java.util.List;

/**
 * Service to filter {@link User}s.
 */
public interface UserService {

    /**
     * Filter the provided {@code users} list by applying the provided {@code gender} filter.<br>
     * Returns the filtered list.
     *
     * @param gender {@link Gender} about the gender filter.
     * @param users  {@link List}&lt;{@link User}&gt; to be filtered.
     * @return the filtered list.
     * @throws EmptyUsersException   exception thrown in case an empty users list has been provided.
     * @throws NoUsersFoundException exception thrown in case, once the filter has been applied, no users have been retained.
     */
    List<User> filterByGender(Gender gender, List<User> users) throws EmptyUsersException, NoUsersFoundException;
}
