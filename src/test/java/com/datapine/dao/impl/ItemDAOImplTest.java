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

import com.datapine.dao.ItemDAO;
import com.datapine.domain.Item;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Tests for {@link ItemDAOImpl}.
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
public class ItemDAOImplTest {

    /**
     * Dao to be tested.
     */
    @Autowired
    private transient ItemDAO dao;

    /**
     * DAO can save an item without any error.
     */
    @Test
    public final void savesWithoutErrors() {
        this.dao.save(new Item("The Title"));
    }

    /**
     * DAO can save and find an item.
     */
    @Test
    public final void findsById() {
        final String title = "The Title 1";
        Item item = this.dao.save(new Item(title));
        item = this.dao.findById(item.getId());
        Assert.assertEquals(title, item.getTitle());
        Assert.assertNotNull(item.getId());
    }

    /**
     * DAO can find all items ordered by id.
     */
    @Test
    public final void findsAll() {
        final List<Item> items = this.dao.findAllOrderById();
        long previd = -1;
        for (final Item item : items) {
            Assert.assertTrue(previd < item.getId());
            previd = item.getId();
        }
    }

}
