package com.eventum.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

public interface StudentService {
  public Map<String, Object> findAllByQuery(StudentListingParamsDto params); 
  public Map<String, Object> getTableInitInfo(); 
  public StudentDTO findById(Long id); 
  public Boolean save(StudentDTO dto); 
  public Boolean deleteById(Long id); 
}
