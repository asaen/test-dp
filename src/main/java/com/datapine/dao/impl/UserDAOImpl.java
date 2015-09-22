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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the {@link UserDAO}.
 *
 * <p>
 * {@link Transactional} annotation at the class level makes all public method
 * in the class transactional.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    /**
     * Entity Manager.
     */
    @PersistenceContext
    private transient EntityManager manager;

    /**
     * Creates new one or updates existing user.
     * @param user The specified user.
     */
    @Override
    public final void save(final User user) {
        this.update(user);
    }

    /**
     * Creates new one or updates existing user.
     * @param user The specified user.
     * @return Created or updated instance.
     */
    @Override
    public final User update(final User user) {
        return this.manager.merge(user);
    }

    /**
     * Removes user from the storage.
     * @param user The specified user.
     */
    @Override
    public final void delete(final User user) {
        User deletable = user;
        if (!this.manager.contains(user)) {
            deletable = this.manager.merge(user);
        }
        this.manager.remove(deletable);
    }

    /**
     * Looks for the user by id.
     * @param identifier The specified id.
     * @return Found user.
     */
    @Override
    public final User findById(final Long identifier) {
        return this.manager.find(User.class, identifier);
    }

    /**
     * Looks for the user by email.
     * @param email The specified email.
     * @return Found user.
     */
    @Override
    public final User findByEmail(final String email) {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<User> query = builder.createQuery(User.class);
        final Root<User> root = query.from(User.class);
        query.where(builder.equal(root.get("email"), email));
        return this.manager.createQuery(query).getResultList().get(0);
    }

    /**
     * Gets an iterator for the collection of all users sorted by id.
     * @return An iterator.
     */
    @Override
    public final Iterator<User> findAllOrderById() {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<User> query = builder.createQuery(User.class);
        final Root<User> root = query.from(User.class);
        query.select(root).orderBy(builder.asc(root.get("id")));
        return this.manager.createQuery(query).getResultList().iterator();
    }

}
