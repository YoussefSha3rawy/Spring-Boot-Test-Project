package com.eventum.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface StudentService{
  public List<StudentDTO> findAll(StudentListingParamsDto params);
  public StudentDTO findById(Long id); 
  public Boolean save(StudentDTO dto); 
  public Boolean update(StudentDTO dto); 
  public Boolean deleteById(Long id); 
}
