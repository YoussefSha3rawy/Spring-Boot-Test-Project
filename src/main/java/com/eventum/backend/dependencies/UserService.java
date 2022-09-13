package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

  public static final String USERNAME = "username";
  public static final String GROUPS_HEIRARCHY = "groupsHierarchy";
  public static final String USER_DIRECTORY = "userDirectory";





  /**
   * Gets currently logged in user with spring security
   *
   * @return User object representing the current logged in user or null
   */
  String getCurrentUserName();


}
