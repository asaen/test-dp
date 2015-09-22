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

import com.datapine.domain.User;
import com.datapine.service.UserService;
import org.hamcrest.core.IsInstanceOf;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Tests for {@link UserServiceImpl}.
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
public class UserServiceImplTest {

    /**
     * Old password.
     */
    private static final String OLD_PASSWORD = "0lDPa$$w0Rd";

    /**
     * New password.
     */
    private static final String NEW_PASSWORD = "N3wPa$$w0Rd";

    /**
     * Expected exception rule to catch nested exceptions.
     */
    @Rule
    // @checkstyle VisibilityModifierCheck (2 lines)
    public final transient ExpectedException expected = ExpectedException
        .none();

    /**
     * Service to be tested.
     */
    @Autowired
    private transient UserService service;

    /**
     * Service can register new user.
     */
    @Test
    public final void registersNewUser() {
        final User user =
            this.service.register("a.b@service.com", OLD_PASSWORD);
        Assert.assertNotNull(user.getId());
    }

    /**
     * Service cannot register new user with the existing email.
     */
    @Test
    public final void doesNotRegisterIfEmailExists() {
        this.expected.expectCause(
            IsInstanceOf.<Throwable>instanceOf(
                ConstraintViolationException.class
            )
        );
        final String email = "c.d@service.com";
        this.service.register(email, OLD_PASSWORD);
        this.service.register(email, NEW_PASSWORD);
    }

    /**
     * Service can update password.
     */
    @Test
    public final void updatesPassword() {
        User user = this.service.register("e.f@service.com", OLD_PASSWORD);
        user =
            this.service.updatePassword(
                user.getId(),
                OLD_PASSWORD,
                NEW_PASSWORD
            );
        Assert.assertEquals(NEW_PASSWORD, user.getPassword());
    }

    /**
     * Service cannot update password if the old one is incorrect.
     */
    @Test(expected = RuntimeException.class)
    public final void doesNotUpdateIfOldPasswordIsWrong() {
        User user = this.service.register("g.h@service.com", OLD_PASSWORD);
        user =
            this.service.updatePassword(
                user.getId(),
                NEW_PASSWORD,
                NEW_PASSWORD
            );
    }

}
