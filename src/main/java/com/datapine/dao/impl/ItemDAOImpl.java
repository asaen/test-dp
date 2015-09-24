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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the {@link ItemDAO}.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Repository
@Transactional
public class ItemDAOImpl implements ItemDAO {

    /**
     * Entity Manager.
     */
    @PersistenceContext
    private transient EntityManager manager;

    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public final Item save(final Item item) {
        return this.manager.merge(item);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#item, 'write')")
    public final void delete(final Item item) {
        Item deletable = item;
        if (!this.manager.contains(item)) {
            deletable = this.manager.merge(item);
        }
        this.manager.remove(deletable);
    }

    @Override
    @PostFilter("hasRole('ROLE_ADMIN') or hasPermission(returnObject, 'read')")
    public final Item findById(final Long iid) {
        return this.manager.find(Item.class, iid);
    }

    @Override
    @PostFilter("hasRole('ROLE_ADMIN') or hasPermission(filterObject, 'read')")
    public final List<Item> findAllOrderById() {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<Item> query = builder.createQuery(Item.class);
        final Root<Item> root = query.from(Item.class);
        query.select(root).orderBy(builder.asc(root.get("id")));
        return this.manager.createQuery(query).getResultList();
    }

}
