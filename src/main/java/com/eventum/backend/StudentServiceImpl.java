package com.eventum.backend;

import com.eventum.backend.dependencies.*;
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

@Service
public class StudentServiceImpl implements StudentService {

  private static final UserLogger USER_LOGGER = new UserLogger(StudentServiceImpl.class);;
  private static final String NAME = "name";
  private static final String LIKE_CHAR = "%";
  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private Mapper mapper;

  @Autowired
  private DynamicQueryService dynamicQueryService;

  @Autowired
  private UserService userService;

  @PersistenceContext
  private EntityManager entityManager;
  @Override
  public Map<String, Object> findAllByQuery(StudentListingParamsDto params) {
    String searchQuery = StringUtils.escapeSQLSearchQuery(params.getSearchQuery());

    DynamicQueryBuilder<Student> dynamicQuery = dynamicQueryService.build(Student.class);

    dynamicQuery.or(new FilterDto<>(NAME, Boolean.FALSE, LIKE_CHAR + searchQuery + LIKE_CHAR));

    dynamicQuery.order(new SortDto(SortDir.valueOf(params.getSortingColumnDirection().toUpperCase()), params.getSortingColumnName()));

    dynamicQuery.paginate(params.getPaginationNumber() * params.getPaginationRange(), params.getPaginationRange());

    List<Student> resultList = dynamicQuery.execute(entityManager);

    return TableListingUtils.generateResultMap(mapper.mapStudents(resultList), dynamicQuery.getResultSize(entityManager));

  }
  @Override
  public Map<String, Object> getTableInitInfo() {
    Map<String, Object> result = new HashMap<>();

    result.put(TableListingParamsDto.COUNT_KEY, studentRepository.count());

    return result;

  }
  @Override
  public StudentDTO findById(Long id) {
    Student entity = studentRepository.findById(id).orElse(null);

    if (entity == null) {

    throw new EntityNotFoundException("Student Not Found.");

    }

    return mapper.mapStudent(entity);

  }
  @Override
  public Boolean save(StudentDTO dto) {
    try {

    studentRepository.save(mapper.mapStudentDto(dto));

    return true;

    }

    catch (Exception e) {

    ApplicationLogger.logError("Error saving Student", e, getClass());

    return false;

    }

  }
  @Override
  public Boolean deleteById(Long id) {
    Student entity = studentRepository.findById(id).orElse(null);

    if (entity == null) {

    throw new EntityNotFoundException("Student Not Found.");

    }

    studentRepository.deleteById(id);

    USER_LOGGER.logDeletingEntity(userService.getCurrentUserName(), LogsAopConstants.AUDITING_TEMPLATES, "" + entity.getId(),

    UserLogger.ADMIN_WORK_FLOW);

    return true;

  }
}
