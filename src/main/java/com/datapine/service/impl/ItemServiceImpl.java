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

import com.datapine.dao.AclDAO;
import com.datapine.dao.ItemDAO;
import com.datapine.domain.Item;
import com.datapine.domain.acl.AclClass;
import com.datapine.domain.acl.AclEntry;
import com.datapine.domain.acl.AclObjectIdentity;
import com.datapine.domain.acl.AclSid;
import com.datapine.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the {@link ItemService}.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    /**
     * Item DAO.
     */
    @Autowired
    private transient ItemDAO dao;

    /**
     * Acl DAO.
     */
    @Autowired
    private transient AclDAO acldao;

    @Override
    public final List<Item> items() {
        return this.dao.findAllOrderById();
    }

    @Override
    public final Item item(final Long iid) {
        return this.dao.findById(iid);
    }

    @Override
    public final Item update(final Item item) {
        final Item merged = this.dao.save(item);
        if (this.acldao.findByItem(merged) == null) {
            final AclClass clazz =
                this.acldao.findClass(Item.class.getCanonicalName());
            final String user = SecurityContextHolder.getContext()
                .getAuthentication().getName();
            AclSid sid = this.acldao.findSid(user);
            if (sid == null) {
                sid = new AclSid(user);
            }
            final AclObjectIdentity ident = new AclObjectIdentity();
            ident.setAclClass(clazz);
            ident.setObjectId(merged.getId());
            ident.setAclSid(sid);
            final AclEntry entry = new AclEntry();
            entry.setAclObject(ident);
            entry.setAclSid(sid);
            entry.setMask(BasePermission.READ.getMask());
            this.acldao.save(entry);
        }
        return merged;
    }

    @Override
    public final void delete(final Item item) {
        this.dao.delete(item);
    }

}
