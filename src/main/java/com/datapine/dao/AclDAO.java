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
package com.datapine.dao;

import com.datapine.domain.Item;
import com.datapine.domain.acl.AclClass;
import com.datapine.domain.acl.AclObjectIdentity;
import com.datapine.domain.acl.AclSid;

/**
 * DAO for all acl entities.
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
public interface AclDAO {

    /**
     * Saves acl entity.
     * @param obj Acl entity.
     * @return Saved entity.
     * @param <T> Entity type.
     */
    <T> T save(T obj);

    /**
     * Finds acl class by class name.
     * @param classname The class name.
     * @return Acl class.
     */
    AclClass findClass(String classname);

    AclSid findSid(String sid);

    /**
     * Finds acl object identity by item.
     * @param item The item/
     * @return Acl object identity.
     */
    AclObjectIdentity findByItem(Item item);

}
