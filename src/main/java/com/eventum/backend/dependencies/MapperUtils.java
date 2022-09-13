package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mmakram
 */
@Component
public class MapperUtils {
  @Autowired
  private DozerBeanMapper dozerBeanMapper;

  public <T, S> List<T> mapList(List<S> source, Class<T> targetClass) {
    if (source == null) {
      return null;
    }
    List<T> target = new ArrayList<>(source.size());
    for (S object : source) {
      target.add(dozerBeanMapper.map(object, targetClass));
    }
    return target;
  }

  public <T, S> TreeSet<T> mapTreeSet(Set<S> source, Class<T> targetClass) {
    if (source == null) {
      return null;
    }
    TreeSet<T> target = new TreeSet<>();
    for (S object : source) {
      target.add(dozerBeanMapper.map(object, targetClass));
    }
    return target;
  }

  public <T, S> Set<T> mapSet(Set<S> source, Class<T> targetClass) {
    if (source == null) {
      return null;
    }
    Set<T> target = new LinkedHashSet<>(source.size());
    for (S object : source) {
      target.add(dozerBeanMapper.map(object, targetClass));
    }
    return target;
  }

  public <T, S> List<T> mapSetIntoList(Set<S> source, Class<T> targetClass) {
    if (source == null) {
      return null;
    }
    List<T> target = new ArrayList<>();
    for (S object : source) {
      target.add(dozerBeanMapper.map(object, targetClass));
    }
    return target;
  }

  public <T, S> Set<T> mapListIntoSet(List<S> source, Class<T> targetClass) {
    if (source == null) {
      return null;
    }
    Set<T> target = new LinkedHashSet<>();
    for (S object : source) {
      target.add(dozerBeanMapper.map(object, targetClass));
    }
    return target;
  }

  public <S, T> T mapEntity(S source, Class<T> targetClass) {
    return dozerBeanMapper.map(source, targetClass);
  }

}
