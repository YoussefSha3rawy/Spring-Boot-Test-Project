package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class DateRangeDto extends HasLongIdDto {
  private Date startDate;
  private Date endDate;
  private Set<TimeRangeDto> timeRanges = new LinkedHashSet<>();

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  public Set<TimeRangeDto> getTimeRanges() {
    return timeRanges;
  }

  public void setTimeRanges(Set<TimeRangeDto> timeRanges) {
    this.timeRanges = timeRanges;
  }

  @Override
  public String toString() {
    return "EventDateRangeDto [startDate=" + startDate + ", endDate=" + endDate + ", eventTimeRanges=" + timeRanges + "]";
  }

}
