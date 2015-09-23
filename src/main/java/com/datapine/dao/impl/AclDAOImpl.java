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
import com.jcabi.log.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
     * Field name.
     */
    private static final String CLAZZ = "clazz";

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
    public final AclClass findClassByClassname(final String classname) {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<AclClass> query =
            builder.createQuery(AclClass.class);
        final Root<AclClass> root = query.from(AclClass.class);
        query.where(builder.equal(root.get(CLAZZ), classname));
        return this.manager.createQuery(query).getResultList().get(0);
    }

    @Override
    public final AclObjectIdentity findByItem(final Item item) {
        AclObjectIdentity result = null;
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<AclObjectIdentity> query =
            builder.createQuery(AclObjectIdentity.class);
        final Root<AclObjectIdentity> root =
            query.from(AclObjectIdentity.class);
        query.where(
            builder.and(
                builder.equal(
                    root.get("aclClass").get(CLAZZ),
                    item.getClass().getCanonicalName()
                ),
                builder.equal(
                    root.get("objectId"),
                    item.getId()
                )
            )
        );
        try {
            result = this.manager.createQuery(query).getSingleResult();
        } catch (final NoResultException ex) {
            Logger.trace(this, "No results found");
        }
        return result;
    }

}
