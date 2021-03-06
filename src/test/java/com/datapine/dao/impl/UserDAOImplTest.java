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
package com.datapine.dao.impl;

import com.datapine.dao.UserDAO;
import com.datapine.domain.User;
import java.util.Iterator;
import javax.persistence.PersistenceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Tests for {@link UserDAOImpl}.
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {
            "classpath:META-INF/spring/applicationContext.xml",
            "classpath:META-INF/spring/applicationPersistence.xml"
        }
    )
@TransactionConfiguration(defaultRollback = true)
public class UserDAOImplTest {

    /**
     * Test password.
     */
    private static final String TEST_PASSWORD = "test";

    /**
     * Dao to be tested.
     */
    @Autowired
    private transient UserDAO dao;

    /**
     * DAO can save a user without any error.
     */
    @Test
    public final void savesWithoutErrors() {
        this.dao.save(new User("a.b@dao.com", TEST_PASSWORD));
    }

    /**
     * DAO can save and find a user.
     */
    @Test
    public final void findsByEmail() {
        final String email = "c.d@dao.com";
        this.dao.save(new User(email, TEST_PASSWORD));
        final User user = this.dao.findByEmail(email);
        Assert.assertEquals(email, user.getEmail());
        Assert.assertNotNull(user.getId());
    }

    /**
     * DAO can find all users.
     */
    @Test
    public final void findsAll() {
        final Iterator<User> iter = this.dao.findAllOrderById();
        long previd = -1;
        while (iter.hasNext()) {
            final User user = iter.next();
            Assert.assertTrue(previd < user.getId());
            previd = user.getId();
        }
    }

    /**
     * DAO cannot save users with the same email.
     */
    @Test(expected = PersistenceException.class)
    public final void doesNotSaveDuplicates() {
        final String email = "e.f@dao.com";
        this.dao.save(new User(email, TEST_PASSWORD));
        this.dao.save(new User(email, TEST_PASSWORD));
    }

}
