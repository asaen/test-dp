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
 * Represents 'acl_entry' table. Stores the actual permissions assigned for each
 * user and domain object.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Entity
@Table(name = "acl_entry")
public class AclEntry {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(
        targetEntity = AclObjectIdentity.class,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinColumn(name = "acl_object_identity", nullable = false)
    private AclObjectIdentity aclObject;

    @Column(name = "ace_order", nullable = false)
    private Integer aceOrder = 1;

    @ManyToOne(
        targetEntity = AclSid.class,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinColumn(name = "sid", nullable = false)
    private AclSid aclSid;

    @Column(name = "mask", nullable = false)
    private Integer mask;

    @Column(name = "granting", nullable = false)
    private Boolean granting = true;

    @Column(name = "audit_success", nullable = false)
    private Boolean auditSuccess = true;

    @Column(name = "audit_failure", nullable = false)
    private Boolean auditFailure = true;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclObjectIdentity getAclObject() {
        return this.aclObject;
    }

    public void setAclObject(AclObjectIdentity aclObject) {
        this.aclObject = aclObject;
    }

    public Integer getAceOrder() {
        return this.aceOrder;
    }

    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }

    public AclSid getAclSid() {
        return this.aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }

    public Integer getMask() {
        return this.mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public Boolean getGranting() {
        return this.granting;
    }

    public void setGranting(Boolean granting) {
        this.granting = granting;
    }

    public Boolean getAuditSuccess() {
        return this.auditSuccess;
    }

    public void setAuditSuccess(Boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    public Boolean getAuditFailure() {
        return this.auditFailure;
    }

    public void setAuditFailure(Boolean auditFailure) {
        this.auditFailure = auditFailure;
    }

}
