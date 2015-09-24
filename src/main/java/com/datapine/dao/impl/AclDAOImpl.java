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
import com.datapine.domain.acl.AclObjectIdentity;
import com.datapine.domain.acl.AclSid;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the {@link AclDAO}.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Repository
@Transactional
public class AclDAOImpl implements AclDAO {

    /**
     * Entity Manager.
     */
    @PersistenceContext
    private transient EntityManager manager;

    @Override
    public final <T> T save(final T obj) {
        return this.manager.merge(obj);
    }

    @Override
    public final AclClass findClass(final String classname) {
        final AclClass obj = new AclClass();
        obj.setClazz(classname);
        return this.findByExample(obj, AclClass.class);
    }

    @Override
    public final AclSid findSid(final String sid) {
        final AclSid obj = new AclSid();
        obj.setSid(sid);
        return this.findByExample(obj, AclSid.class);
    }

    @Override
    public final AclObjectIdentity findByItem(final Item item) {
        final AclObjectIdentity obj = new AclObjectIdentity();
        obj.setAclClass(new AclClass(item.getClass().getCanonicalName()));
        obj.setObjectId(item.getId());
        return this.findByExample(obj, AclObjectIdentity.class);
    }

    /**
     * Finds an entity by example.
     * @param obj Example.
     * @param clazz Class.
     * @param <T> Entity type.
     * @return Found entity.
     */
    @SuppressWarnings("unchecked")
    private <T> T findByExample(final T obj, final Class<T> clazz) {
        final Session session = (Session) this.manager.getDelegate();
        final Example example = Example.create(obj);
        final Criteria criteria = session.createCriteria(clazz).add(example);
        return (T) criteria.uniqueResult();
    }

}
