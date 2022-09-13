/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/
package com.eventum.backend.dependencies;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class HasLongIdDto implements com.eventum.backend.extradependencies.HasId<Long>, Serializable {

  protected Long id;

  private Long version;
  private static final String UPDATING_OPERATION = "updating";
  private static final String CREATING_OPERATION = "creating";

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  public String getCurrentOperation() {
    return this.getId() == null ? CREATING_OPERATION : UPDATING_OPERATION;
  }

  @Override
  public String toString() {
    if (this.getId() != null) {
      return this.getId().toString();
    }
    return "";
  }
}
