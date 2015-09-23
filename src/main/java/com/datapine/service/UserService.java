package com.datapine.service;

import com.datapine.domain.User;
import java.util.List;

public interface UserService {

    User register(String email, String password);

    User updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Returns a list of the existing users.
     * @return A list.
     */
    List<User> users();

    /**
     * Looks for a user by id.
     * @param uid The specified user id.
     * @return A user.
     */
    User user(Long uid);

    /**
     * Updates the specified user.
     * @param user The specified user.
     * @return A user.
     */
    User update(User user);

    /**
     * Removes the specified user.
     * @param user The specified user.
     */
    void delete(User user);

}
