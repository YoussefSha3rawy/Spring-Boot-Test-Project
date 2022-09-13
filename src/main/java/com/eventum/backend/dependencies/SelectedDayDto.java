package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SelectedDayDto extends HasLongIdDto{

  private Short day;

  public Short getDay() {
    return day;
  }

  public void setDay(Short day) {
    this.day = day;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(getId()).append(getDay()).hashCode();
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(this.getClass().equals(object.getClass()))) {
      return false;
    }
    SelectedDayDto other = (SelectedDayDto) object;
    if (this.getId() != null && other.getId() != null) {
      return this.getId().equals(other.getId());
    }
    if (this.getId() == null && other.getId() == null) {
      EqualsBuilder equalsBuilder = new EqualsBuilder();
      equalsBuilder.append(this.getDay(), other.getDay());
      return equalsBuilder.isEquals();
    }
    return false;
  }

  public String toString() {
    return day.toString();
  }

  public void put(int i, Object v) {
    switch (i) {
      case 0:
        this.setDay(((Integer) v).shortValue());
        break;
    }

  }

  public Object get(int i) {
    switch (i) {
      case 0:
        return (int) this.getDay();
    }
    return null;
  }


}
