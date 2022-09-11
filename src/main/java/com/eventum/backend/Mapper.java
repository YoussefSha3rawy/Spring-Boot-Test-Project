package com.eventum.backend;

public class Mapper{
  public static StudentDTO mapToDTO(Student entity) {
    StudentDTO dto = new StudentDTO();
    dto.setId( entity.getId());
    dto.setName( entity.getName());
    return dto;
  }

  public static Student mapFromDTO(StudentDTO dto) {
    Student entity = new Student();
    entity.setId( dto.getId());
    entity.setName( dto.getName());
    return entity;
  }

}
