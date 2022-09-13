package com.eventum.backend;

import com.eventum.backend.dependencies.ENMSRestResponseHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping(value="/student")
@CrossOrigin
public class StudentController {
  @Autowired
  private StudentService studentService;

  @Autowired
  private ENMSRestResponseHandler responseHandler;

  @ResponseBody
  @RequestMapping(method=RequestMethod.GET, value="/tableData/initiate")
  public Map<String, Object> loadAllStudent() {
    return studentService.getTableInitInfo();

  }
  @ResponseBody
  @RequestMapping(method=RequestMethod.POST, value="/tableData")
  public ResponseEntity<Map<String, Object>> getStudentByQuery(@RequestBody StudentListingParamsDto params) {
    return responseHandler.handleJsonOKStatus(studentService.findAllByQuery(params));

  }
  @ResponseBody
  @RequestMapping(method=RequestMethod.GET, value="/{id}")
  public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
    return responseHandler.handleJsonOKStatus(studentService.findById(id));

  }
  @ResponseBody
  @RequestMapping(method=RequestMethod.POST, value="/")
  public ResponseEntity<Boolean> saveStudentEntities(@RequestBody StudentDTO entity) {
    return responseHandler.handleJsonOKStatus(studentService.save(entity));

  }
  @ResponseBody
  @RequestMapping(method=RequestMethod.PUT, value="/")
  public ResponseEntity<Boolean> updateStudentEntities(@RequestBody StudentDTO entity) {
    return responseHandler.handleJsonOKStatus(studentService.save(entity));

  }
  @ResponseBody
  @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
  public ResponseEntity<Boolean> deleteStudentById(@PathVariable Long id) {
    return responseHandler.handleJsonOKStatus(studentService.deleteById(id));

  }
}
