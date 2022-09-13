package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicQueryService {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  public <T> DynamicQueryBuilder<T> build(Class<T> clazz) {
    return DynamicQueryBuilder.<T>build(entityManagerFactory, clazz);
  }

  public <T> DynamicTupleQueryBuilder<T> buildTupleQuery(Class<T> clazz) {
    return DynamicTupleQueryBuilder.<T>build(entityManagerFactory, clazz);
  }

}
