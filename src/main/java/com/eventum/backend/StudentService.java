package com.eventum.backend;

public interface StudentService {
  public Iterable<Student> findAll(EntityExampleListingParamsDto params);
  public Student findById(Integer id); 
  public void save(Student entity); 
  public void update(Student entity); 
  public void deleteById(Integer id); 
}
