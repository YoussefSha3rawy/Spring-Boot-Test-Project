package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BasicEntityListener {

  @PrePersist
  public void userPrePersist(BasicEntity entity) {
    Long createUserId = getCurrentAuthenticatedUserId();
    entity.setCreateUserId(createUserId);
    entity.setUpdateUserId(createUserId);
  }

  @PreUpdate
  public void userPreUpdate(BasicEntity entity) {
    entity.setUpdateUserId(getCurrentAuthenticatedUserId());
  }

  private static Long getCurrentAuthenticatedUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof AnonymousAuthenticationToken || auth == null) {
      return null;
    }
    return ((AuthUser) auth.getPrincipal()).getId();
  }
}
