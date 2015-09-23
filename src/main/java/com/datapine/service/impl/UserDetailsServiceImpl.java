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
import com.jcabi.log.Logger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Loads user-specific data from the database and assigns roles.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * DAO to access users.
     */
    @Autowired
    private transient UserDAO dao;

    /**
     * Unmodifiable list of admin users.
     */
    private final transient List<User> admins = Collections.unmodifiableList(
        Arrays.asList(
            // @checkstyle MultipleStringLiteralsCheck (2 lines)
            new User("admin@dp.com", "admin"),
            new User("admin@gmail.com", "admin")
        )
    );

    /**
     * Initialization method to prepare some test data.
     */
    @PostConstruct
    public final void init() {
        for (final User admin : this.admins) {
            this.dao.save(admin);
        }
        this.dao.save(new User("a", "a1"));
        this.dao.save(new User("b", "b1"));
        Logger.debug(this, "Init method executed.");
    }

    @Override
    public final UserDetails loadUserByUsername(final String username) {
        UserDetails result = null;
        String role = "ROLE_USER";
        if (this.isAdmin(username)) {
            role = "ROLE_ADMIN";
        }
        final User user = this.dao.findByEmail(username);
        if (user != null) {
            result = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(role)
            );
        }
        return result;
    }

    /**
     * Checks if the specified user name is in the admin list.
     * @param username The specified user name.
     * @return Either {@code true} or {@code false}.
     */
    private boolean isAdmin(final String username) {
        boolean result = false;
        for (final User user : this.admins) {
            if (user.getEmail().equalsIgnoreCase(username)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
