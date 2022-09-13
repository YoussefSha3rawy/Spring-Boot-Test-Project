/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/
package com.eventum.backend.dependencies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TableListingParamsDto {
  public static final String COUNT_KEY = "count";
  public static final String DATA_KEY = "data";
  public static final String TYPE = "type";

  protected static final int DEFAULT_PAGINATION_NUMBER = 0;
  protected static final int DEFAULT_PAGINATION_RANGE = 10;
  protected static final String DEFAULT_SORTING_DIRECTION = "asc";

  private String filters;
  private String sortingColumnDirection = getDefaultSortingColumnDirection();
  private Integer paginationRange = DEFAULT_PAGINATION_RANGE;
  private Integer paginationNumber = DEFAULT_PAGINATION_NUMBER;
  private String sortingColumnName = getDefaultSortingColumnName();
  private Map<String, List<String>> selectedFilters = new HashMap<>();

  public String getFilters() {
    return filters;
  }

  public void setFilters(String filters) {
    this.filters = filters;
  }

  public String getSortingColumnDirection() {
    return sortingColumnDirection;
  }

  public void setSortingColumnDirection(String sortingColumnDirection) {
    this.sortingColumnDirection = sortingColumnDirection;
  }

  public Integer getPaginationRange() {
    return paginationRange;
  }

  public void setPaginationRange(Integer paginationRange) {
    this.paginationRange = paginationRange;
  }

  public Integer getPaginationNumber() {
    return paginationNumber;
  }

  public void setPaginationNumber(Integer paginationNumber) {
    this.paginationNumber = paginationNumber;
  }

  public String getSortingColumnName() {
    return sortingColumnName;
  }

  public void setSortingColumnName(String sortingColumnName) {
    this.sortingColumnName = sortingColumnName;
  }

  public Map<String, List<String>> getSelectedFilters() {
    return selectedFilters;
  }

  public void setSelectedFilters(Map<String, List<String>> selectedFilters) {
    this.selectedFilters = selectedFilters;
  }

  public abstract String getDefaultSortingColumnName();

  protected String getDefaultSortingColumnDirection() {
    return DEFAULT_SORTING_DIRECTION;
  }

}
