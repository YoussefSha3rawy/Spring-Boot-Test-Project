/*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/
package com.eventum.backend.dependencies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class FilterDto<M> {

  private String key;
  private List<M> value;
  private FilterType filterType = FilterType.LIKE;
  private boolean includeNullValue;
  private Class type;
  private Set<DateRangeDto> dateRanges = new LinkedHashSet<>();
  private Set<TimeRangeDto> timeRanges = new LinkedHashSet<>();
  private Set<SelectedDayDto> selectedDays = new HashSet<>();

  public FilterDto(String key, List<M> value, FilterType filterType, boolean includeNullValue, Class type) {
    super();
    this.key = key;
    this.value = value;
    this.filterType = filterType;
    this.includeNullValue = includeNullValue;
    this.setType(type);
  }

  public FilterDto(String key, boolean includeNullValue, M... value) {
    this(key, Arrays.asList(value), FilterType.LIKE, includeNullValue, null);
  }

  public FilterDto(String key, List<M> value, boolean includeNullValue) {
    this(key, value, FilterType.LIKE, includeNullValue, null);
  }

  public FilterDto(String key, List<M> value, FilterType filterType) {
    this(key, value, filterType, Boolean.FALSE, null);
  }

  public FilterDto(String key, List<M> value) {
    this(key, value, FilterType.LIKE);
  }

  public FilterDto(String key, FilterType type) {
    this(key, null, type, Boolean.FALSE, null);
  }

  public FilterType getFilterType() {
    return filterType;
  }

  public String getKey() {
    return key;
  }

  public List<M> getValue() {
    return value;
  }

  public boolean isIncludeNullValue() {
    return includeNullValue;
  }

  public Class getType() {
    return type;
  }

  public void setType(Class type) {
    this.type = type;
  }

  public Set<DateRangeDto> getDateRanges() {
    return dateRanges;
  }

  public void setDateRanges(Set<DateRangeDto> dateRanges) {
    this.dateRanges = dateRanges;
  }

  public Set<TimeRangeDto> getTimeRanges() {
    return timeRanges;
  }

  public void setTimeRanges(Set<TimeRangeDto> timeRanges) {
    this.timeRanges = timeRanges;
  }

  public Set<SelectedDayDto> getSelectedDays() {
    return selectedDays;
  }

  public void setSelectedDays(Set<SelectedDayDto> selectedDays) {
    this.selectedDays = selectedDays;
  }
}
