package com.eventum.backend;

import com.eventum.backend.EntityExampleListingParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  @Transactional
  public Iterable<Student> findAll(EntityExampleListingParamsDto params) {
    // String searchQuery = StringUtils.escapeSQLSearchQuery(params.getSearchQuery());
    // DynamicQueryBuilder<Student> dynamicQuery = dynamicQueryService.build(Student.class);
    // dynamicQuery.or(new FilterDto<>(NAME, Boolean.FALSE, LIKE_CHAR + searchQuery + LIKE_CHAR));
    // dynamicQuery.order(new SortDto(SortDir.valueOf(params.getSortingColumnDirection().toUpperCase()), params.getSortingColumnName()));
    // dynamicQuery.paginate(params.getPaginationNumber() * params.getPaginationRange(), params.getPaginationRange());
    // List<Student> resultList = dynamicQuery.execute(entityManager);
    // return TableListingUtils.generateResultMap(mapper.mapEntityExamples(resultList), dynamicQuery.getResultSize(entityManager));
    return  studentRepository.findAll();
  }

  @Override
  @Transactional
  public Student findById(Integer id) {
    Optional<Student> opt = studentRepository.findById(id);
    return  opt.isPresent()? opt.get(): null;
  }

  @Override
  @Transactional
  public void save(Student entity) {
    studentRepository.save(entity);
  }

  @Override
  @Transactional
  public void update(Student entity) {
    studentRepository.save(entity);
  }

  @Override
  @Transactional
  public void deleteById(Integer id) {
    studentRepository.deleteById(id);
  }

}
