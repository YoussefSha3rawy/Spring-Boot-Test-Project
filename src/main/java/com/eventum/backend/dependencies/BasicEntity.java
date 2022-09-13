package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@EntityListeners(BasicEntityListener.class)
@MappedSuperclass
public abstract class BasicEntity implements Serializable {

  @Version
  private Long version;

  @CreationTimestamp
  @Column(name = "create_date_time", updatable = false)
  @Basic(fetch = FetchType.LAZY)
  private Timestamp createDateTime;

  @UpdateTimestamp
  @Column(name = "update_date_time")
  @Basic(fetch = FetchType.LAZY)
  private Timestamp updateDateTime;

  @Column(name = "create_user_id", updatable = false)
  private Long createUserId;

  @Column(name = "update_user_id")
  private Long updateUserId;

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Timestamp getCreateDateTime() {
    return createDateTime;
  }

  public void setCreateDateTime(Timestamp createDateTime) {
    this.createDateTime = createDateTime;
  }

  public Timestamp getUpdateDateTime() {
    return updateDateTime;
  }

  public void setUpdateDateTime(Timestamp updateDateTime) {
    this.updateDateTime = updateDateTime;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Long getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(Long updateUserId) {
    this.updateUserId = updateUserId;
  }
}
