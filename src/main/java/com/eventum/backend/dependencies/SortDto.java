package com.eventum.backend.dependencies;

/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

public class SortDto {

  private SortDir sortDir;
  private String[] sortFields;

  public SortDto(SortDir sortDir, String... sortFields) {
    super();
    this.sortDir = sortDir;
    this.sortFields = sortFields;
  }

  public SortDir getSortDir() {
    return sortDir;
  }

  public String[] getSortFields() {
    return sortFields;
  }

}
