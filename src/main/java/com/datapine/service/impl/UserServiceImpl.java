/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Alexey Saenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.datapine.service.impl;

import com.datapine.dao.UserDAO;
import com.datapine.domain.User;
import com.datapine.service.UserService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the {@link UserService}.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * User DAO.
     */
    @Autowired
    private transient UserDAO dao;

    /**
     * Registers new user.
     * @param email The specified email.
     * @param password The specified password.
     * @return A new {@link User} instance.
     */
    @Override
    public final User register(final String email, final String password) {
        return this.dao.update(new User(email, password));
    }

    /**
     * Updates password. TODO fix warning.
     *
     * @param userid Identifier of the user.
     * @param oldpwd The old password.
     * @param newpwd The new password.
     * @return The user with updated password.
     */
    @Override
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public final User updatePassword(final Long userid,
        final String oldpwd, final String newpwd) {
        final User user = this.dao.findById(userid);
        if (!StringUtils.equals(oldpwd, user.getPassword())) {
            throw new RuntimeException();
        }
        user.setPassword(newpwd);
        return this.dao.update(user);
    }

    @Override
    public final List<User> users() {
        final Iterator<User> iter = this.dao.findAllOrderById();
        final List<User> result = new ArrayList<User>();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    @Override
    public User user(final Long uid) {
        return this.dao.findById(uid);
    }

    @Override
    public User update(User user) {
        return this.dao.update(user);
    }

    @Override
    public final void delete(final User user) {
        this.dao.delete(user);
    }

}
