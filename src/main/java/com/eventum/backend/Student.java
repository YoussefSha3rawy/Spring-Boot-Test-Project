package com.eventum.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Student")
public class Student {

  @Column(name = "id", columnDefinition = "INT")
  @Id
  private int id;

  @Column(name = "name", columnDefinition = "VARCHAR")
  private String name;

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return  this.id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return  this.name;
  }

}
