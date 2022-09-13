package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.sql.Time;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeRangeDto extends HasLongIdDto {

  private Time startTime;

  private Time endTime;

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public String toString() {
    return "{\"startTime\": \"" + startTime + "\", \"endTime\": \"" + endTime + "\"}";
  }

  public void put(int i, Object v) {
    switch (i) {
      case 0:
        this.setStartTime(Time.valueOf(v.toString()));
        break;
      case 1:
        this.setEndTime(Time.valueOf(v.toString()));
        break;
    }
  }

  public Object get(int i) {
    switch (i) {
      case 0:
        return this.getStartTime().toString();
      case 1:
        return this.getEndTime().toString();
    }
    return null;
  }

}
