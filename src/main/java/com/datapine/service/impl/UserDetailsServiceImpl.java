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

import com.datapine.dao.AclDAO;
import com.datapine.dao.ItemDAO;
import com.datapine.dao.UserDAO;
import com.datapine.domain.Item;
import com.datapine.domain.User;
import com.datapine.domain.acl.AclClass;
import com.datapine.domain.acl.AclEntry;
import com.datapine.domain.acl.AclObjectIdentity;
import com.datapine.domain.acl.AclSid;
import com.jcabi.log.Logger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
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
    private transient UserDAO userdao;

    @Autowired
    private transient ItemDAO itemdao;

    @Autowired
    private transient AclDAO acldao;

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

    private final transient List<User> users = Collections.unmodifiableList(
        Arrays.asList(
            new User("aaaa@dp.com", "aaaa"),
            new User("bbbb@dp.com", "bbbb"),
            new User("cccc@dp.com", "cccc")
        )
    );

    private final transient List<User> guests = Collections.unmodifiableList(
        Arrays.asList(
            new User("dddd@dp.com", "dddd"),
            new User("eeee@dp.com", "eeee")
        )
    );

    /**
     * Initialization method to prepare some test data.
     */
    @PostConstruct
    public final void init() {
        this.registerUsers(this.admins);
        this.registerUsers(this.users);
        this.registerUsers(this.guests);
        this.acldao.save(new AclClass(Item.class.getCanonicalName()));

        final AclClass clazz = new AclClass(Item.class.getCanonicalName());

        AclSid sid = new AclSid(this.admins.get(0).getEmail(), true);

        AclObjectIdentity ident = new AclObjectIdentity();
        ident.setAclClass(clazz);
        ident.setObjectId(this.itemdao.save(new Item("Title 1")).getId());
        ident.setAclSid(sid);

        AclEntry entry = new AclEntry();
        entry.setAclObject(ident);
        entry.setAclSid(sid);
        entry.setMask(BasePermission.WRITE.getMask() | BasePermission.READ.getMask());

        Logger.debug(this, "Init method executed.");
    }

    @Override
    public final UserDetails loadUserByUsername(final String username) {
        UserDetails result = null;
        String role = "ROLE_GUEST";
        if (this.admins.contains(new User(username))) {
            role = "ROLE_ADMIN";
        } else if (this.users.contains(new User(username))) {
            role = "ROLE_USER";
        }
        final User user = this.userdao.findByEmail(username);
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
     * Registers new users.
     * @param usrs Collection of users.
     */
    private void registerUsers(Iterable<User> usrs) {
        for (final User user : usrs) {
            this.userdao.save(user);
        }
    }

}
