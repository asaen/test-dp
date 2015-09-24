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

import com.datapine.dao.AclDAO;
import com.datapine.domain.Item;
import com.datapine.domain.acl.AclClass;
import com.datapine.domain.acl.AclEntry;
import com.datapine.domain.acl.AclObjectIdentity;
import com.datapine.domain.acl.AclSid;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Tests for {@link AclDAOImpl}.
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
public class AclDAOImplTest {

    /**
     * Dao to be tested.
     */
    @Autowired
    private transient AclDAO dao;

    /**
     * DAO can save an acl entry without any error.
     */
    @Test
    public final void savesEntryWithoutErrors() {
        final AclClass clazz = new AclClass(this.getClass().getCanonicalName());
        final AclSid sid = new AclSid("dddd@dp.com", true);
        final AclObjectIdentity ident = new AclObjectIdentity();
        ident.setAclClass(clazz);
        final Long obj = 123L;
        ident.setObjectId(obj);
        ident.setAclSid(sid);
        final AclEntry entry = new AclEntry();
        entry.setAclObject(ident);
        entry.setAclSid(sid);
        entry.setMask(
            BasePermission.WRITE.getMask() | BasePermission.READ.getMask()
        );
        final AclEntry found = this.dao.save(entry);
        Assert.assertNotNull(found.getId());
        Assert.assertNotNull(found.getAclObject().getId());
        Assert.assertNotNull(found.getAclSid().getId());
        Assert.assertNotNull(found.getAclObject().getAclClass().getId());
    }

    /**
     * DAO can find acl object identity related to the specified item.
     */
    @Test
    public final void findsAclObjectIdentityByItem() {
        final AclClass clazz = new AclClass(this.getClass().getCanonicalName());
        final AclSid sid = new AclSid("aa@dp.com", true);
        final AclObjectIdentity ident = new AclObjectIdentity();
        ident.setAclClass(clazz);
        final Long obj = 10L;
        ident.setObjectId(obj);
        ident.setAclSid(sid);
        this.dao.save(ident);
        final Item item = new Item();
        item.setId(obj);
        final AclObjectIdentity found = this.dao.findByItem(item);
        Assert.assertNotNull(found);
    }

    /**
     * DAO cannot find acl object identity.
     */
    @Test
    public final void doesNotFindAclObjectIdentityByItem() {
        final Item item = new Item();
        final Long iid = 99L;
        item.setId(iid);
        Assert.assertNull(this.dao.findByItem(item));
    }

    @Test
    public final void findAclSid() {
        final String sid = "aaa";
        final AclSid aclsid = new AclSid(sid);
        this.dao.save(aclsid);
        Assert.assertNotNull(this.dao.findSid(sid));
    }

}
