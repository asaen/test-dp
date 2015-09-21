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

    @PersistenceContext
    private EntityManager manager;

    /** {@inheritDoc} */
    @Override
    public void save(User user) {
        if (user.getId() == null) {
            this.manager.persist(user);
        } else {
            this.manager.merge(user);
        }
    }

    /** {@inheritDoc} */
    @Override
    public User update(User user) {
        return this.manager.merge(user);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(User user) {
        this.manager.remove(user);
    }

    /** {@inheritDoc} */
    @Override
    public User findById(Long id) {
        return this.manager.find(User.class, id);
    }

    /** {@inheritDoc} */
    @Override
    public User findByEmail(String email) {
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(builder.equal(root.get("email"), email));
        return this.manager.createQuery(query).getResultList().get(0);
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<User> findAllOrderById() {
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).orderBy(builder.asc(root.get("id")));
        return this.manager.createQuery(query).getResultList().iterator();
    }

}
