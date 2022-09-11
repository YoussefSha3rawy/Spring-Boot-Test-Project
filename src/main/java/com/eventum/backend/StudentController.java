package com.eventum.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class StudentController{

  @Autowired
  private StudentService studentService;

//  @Autowired
//  private ENMSRestResponseHandler responseHandler;

  @RequestMapping(method = RequestMethod.POST, value = "/tableData")
  public ResponseEntity<Iterable<StudentDTO>> findAll(@RequestBody StudentListingParamsDto params) {
    // return responseHandler.handleJsonOKStatus(returnstudentService.findAll(params));
    return new ResponseEntity(studentService.findAll(params),
    new HttpHeaders(){{setContentType(MediaType.APPLICATION_JSON);}},HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  public ResponseEntity<StudentDTO> findById(@PathVariable Long id) {
    // return responseHandler.handleJsonOKStatus(studentService.findById(id));
    return new ResponseEntity(studentService.findById(id),
    new HttpHeaders(){{setContentType(MediaType.APPLICATION_JSON);}},HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/")
  public ResponseEntity<Boolean> save(@RequestBody StudentDTO entity) {
    // return responseHandler.handleJsonOKStatus(studentService.save(entity));
    return new ResponseEntity(studentService.save(entity),
    new HttpHeaders(){{setContentType(MediaType.APPLICATION_JSON);}},HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/")
  public ResponseEntity<Boolean> update(@RequestBody StudentDTO entity) {
    // return responseHandler.handleJsonOKStatus(studentService.update(entity));
    return new ResponseEntity(studentService.update(entity),
    new HttpHeaders(){{setContentType(MediaType.APPLICATION_JSON);}},HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
    // return responseHandler.handleJsonOKStatus(studentService.deleteById(id));
    return new ResponseEntity(studentService.deleteById(id),
    new HttpHeaders(){{setContentType(MediaType.APPLICATION_JSON);}},HttpStatus.OK);
  }

}
