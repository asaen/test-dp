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

import com.datapine.domain.Item;
import com.datapine.service.ItemService;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Tests for {@link ItemServiceImpl}.
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
public class ItemServiceImplTest {

    /**
     * Service to be tested.
     */
    @Autowired
    private transient ItemService service;

    /**
     * Before tests.
     */
    @Before
    public final void before() {
        SecurityContextHolder.getContext().setAuthentication(
            new AnonymousAuthenticationToken(
                "key",
                "test user",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            )
        );
    }

    /**
     * Service can save item.
     */
    @Test
    public final void savesItem() {
        final Item item = this.service.update(new Item("The Title"));
        Assert.assertNotNull(item.getId());
    }

    /**
     * Service can update item.
     */
    @Test
    public final void updatesItem() {
        final String otitle = "Old Title";
        final String ntitle = "New Title";
        final Item item = this.service.update(new Item(otitle));
        Assert.assertNotNull(item.getId());
        Assert.assertEquals(otitle, item.getTitle());
        item.setTitle(ntitle);
        this.service.update(item);
        final Item found = this.service.item(item.getId());
        Assert.assertEquals(item.getId(), found.getId());
        Assert.assertEquals(ntitle, found.getTitle());
    }

}
