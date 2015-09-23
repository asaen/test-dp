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
package com.datapine.domain.acl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents 'acl_object_identity' table. Stores the actual identities of the
 * domain objects. The identities are referenced via a unique id which is
 * retrieved from another database.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Entity
@Table(name = "acl_object_identity")
public class AclObjectIdentity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(
        targetEntity = AclClass.class,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinColumn(name = "object_id_class", nullable = false)
    private AclClass aclClass;

    @Column(name = "object_id_identity", nullable = false)
    private Long objectId;

    @ManyToOne(
        targetEntity = AclObjectIdentity.class,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinColumn(name = "parent_object")
    private AclObjectIdentity parentAclObject;

    @ManyToOne(
        targetEntity = AclSid.class,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinColumn(name = "owner_sid")
    private AclSid aclSid;

    @Column(name = "entries_inheriting", nullable = false)
    private Boolean inheriting = false;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclClass getAclClass() {
        return this.aclClass;
    }

    public void setAclClass(AclClass aclClass) {
        this.aclClass = aclClass;
    }

    public Long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public AclObjectIdentity getParentAclObject() {
        return this.parentAclObject;
    }

    public void setParentAclObject(AclObjectIdentity parentAclObject) {
        this.parentAclObject = parentAclObject;
    }

    public AclSid getAclSid() {
        return this.aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }

    public Boolean getInheriting() {
        return this.inheriting;
    }

    public void setInheriting(Boolean inheriting) {
        this.inheriting = inheriting;
    }

}
