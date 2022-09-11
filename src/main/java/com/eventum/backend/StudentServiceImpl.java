package com.eventum.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

  @Autowired
  private StudentRepository studentRepository;

  @Override
  @Transactional
  public List<StudentDTO> findAll(StudentListingParamsDto params) {
    // String searchQuery = StringUtils.escapeSQLSearchQuery(params.getSearchQuery());
    // DynamicQueryBuilder<Student> dynamicQuery = dynamicQueryService.build(Student.class);
    // dynamicQuery.or(new FilterDto<>(NAME, Boolean.FALSE, LIKE_CHAR + searchQuery + LIKE_CHAR));
    // dynamicQuery.order(new SortDto(SortDir.valueOf(params.getSortingColumnDirection().toUpperCase()), params.getSortingColumnName()));
    // dynamicQuery.paginate(params.getPaginationNumber() * params.getPaginationRange(), params.getPaginationRange());
    // List<Student> resultList = dynamicQuery.execute(entityManager);
    // return TableListingUtils.generateResultMap(mapper.mapEntityExamples(resultList), dynamicQuery.getResultSize(entityManager));
    return ((List<Student>)studentRepository.findAll())
    .stream()
    .map(x -> Mapper.mapToDTO(x))
    .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public StudentDTO findById(Long id) {
    Optional<Student> opt = studentRepository.findById(id);
    return opt.isPresent()?Mapper.mapToDTO(opt.get()): null;
  }

  @Override
  @Transactional
  public Boolean save(StudentDTO dto) {
    Student entity = Mapper.mapFromDTO(dto);
    studentRepository.save(entity);
    return true;
  }

  @Override
  @Transactional
  public Boolean update(StudentDTO dto) {
    Student entity = Mapper.mapFromDTO(dto);
    studentRepository.save(entity);
    return true;
  }

  @Override
  @Transactional
  public Boolean deleteById(Long id) {
    Optional<Student> res = studentRepository.findById(id);
    if(res.isPresent()){
      studentRepository.deleteById(id);
      return true;
    }
    return false;
  }

}
