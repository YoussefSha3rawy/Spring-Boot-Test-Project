package com.eventum.backend;

import org.hibernate.annotations.Type;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Student")
public class Student {

  @Column(name="id")
  @Type(type="org.hibernate.type.LongType")
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @Column(name="name")
  @Type(type="org.hibernate.type.StringType")
  private String name;

  public void setId(long id) {
    this.id = id;
  }
  public long getId() {
    return this.id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return this.name;
  }
}
