package com.eventum.backend;

import com.eventum.backend.dependencies.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.dozer.DozerBeanMapper;

@Service
public class Mapper {
  @Autowired
  private DozerBeanMapper dozerBeanMapper;

  @Autowired
  private MapperUtils mapper;

  public  Student mapStudentDto(StudentDTO dto) {
    return dozerBeanMapper.map(dto, Student.class);
  }
  public  StudentDTO mapStudent(Student entity) {
    return dozerBeanMapper.map(entity, StudentDTO.class);
  }
  public List<StudentDTO> mapStudents(List<Student> entities) {
    return mapper.mapList(entities, StudentDTO.class);
  }
}
