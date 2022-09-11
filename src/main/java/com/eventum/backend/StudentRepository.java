package com.eventum.backend;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface StudentRepository extends CrudRepository<Student,Long>{

}
