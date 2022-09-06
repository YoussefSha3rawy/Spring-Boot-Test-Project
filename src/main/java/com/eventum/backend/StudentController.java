package com.eventum.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class StudentController {

  @Autowired
  private StudentService studentService;

//  @Autowired
  // private ENMSRestResponseHandler responseHandler;

  @RequestMapping(method = RequestMethod.POST, value = "/tableData")
  public Iterable<Student> findAll(@RequestBody EntityExampleListingParamsDto params) {
    return studentService.findAll(params);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  public Student findById(@PathVariable Integer id) {
    return studentService.findById(id);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/")
  public void save(@RequestBody Student entity) {
    studentService.save(entity);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/")
  public void update(@RequestBody Student entity) {
    studentService.update(entity);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public void deleteById(@PathVariable Integer id) {
    studentService.deleteById(id);
  }

}
