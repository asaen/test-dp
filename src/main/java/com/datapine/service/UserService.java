package com.datapine.service;

import com.datapine.domain.User;
import java.util.Iterator;

public interface UserService {

    User register(String email, String password);

    User updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Returns an iterator over the existing users.
     * @return An iterator.
     */
    Iterator<User> users();

    /**
     * Removes the specified user.
     * @param user The specified user.
     */
    void delete(User user);

}
