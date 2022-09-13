package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUser implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String password;
  private boolean deleted;

  public AuthUser(Long id, String username, String password, boolean deleted) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.deleted = deleted;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !deleted;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !deleted;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !deleted;
  }

  @Override
  public boolean isEnabled() {
    return !deleted;
  }

  public Long getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<GrantedAuthority>();
  }

}
