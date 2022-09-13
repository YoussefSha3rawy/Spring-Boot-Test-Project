package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class    DynamicQueryBuilder<T> {

  protected EntityManagerFactory entityManagerFactory;

  protected CriteriaBuilder builder;

  private CriteriaQuery<T> query;

  private TypedQuery<T> typedQuery;

  protected Root<T> root;

  protected List<Predicate> overallFilters = new ArrayList<>();

  protected int firstRowIndex = -1;

  protected int pageSize = -1;

  protected CriteriaQuery<Long> countQuery;

  protected Root<T> countQueryExp;

  private String nativeQuery;

  public static <T> DynamicQueryBuilder<T> build(EntityManagerFactory entityManagerFactory, Class<T> clazz) {
    return new DynamicQueryBuilder<>(entityManagerFactory, clazz);
  }

  DynamicQueryBuilder(EntityManagerFactory entityManagerFactory, Class<T> clazz) {
    this.entityManagerFactory = entityManagerFactory;
    this.builder = entityManagerFactory.getCriteriaBuilder();
    this.query = builder.createQuery(clazz);
    this.root = query.from(clazz);
    this.query.select(root);
    this.countQuery = builder.createQuery(Long.class);
    this.countQueryExp = countQuery.from(clazz);
  }

  public DynamicQueryBuilder() {}

  public DynamicQueryBuilder(String nativeQuery) {
    this.nativeQuery = nativeQuery;
  }

  public DynamicQueryBuilder<T> distinct() {
    this.query.distinct(true);
    return this;
  }

  public void joinCollection(String collectionName, String collectionAttributeKey, Set<?> values) {
    SetJoin<Object, Object> joinSet = root.joinSet(collectionName);
    countQueryExp.joinSet(collectionName);
    overallFilters.add(getPath(joinSet, collectionAttributeKey).in(values));
  }

  public Pair<Join<Object, Object>, Join<Object, Object>> joinEntity(String entityName, String entityKey) {
    return joinEntity(entityName, entityKey, null);
  }

  public Pair<Join<Object, Object>, Join<Object, Object>> joinEntity(String entityName, String entityKey, Set<?> values) {
    Join<Object, Object> join = root.join(entityName);
    Join<Object, Object> countJoin = countQueryExp.join(entityName);
    if (values != null) {
      overallFilters.add(getPath(join, entityKey).in(values));
    }
    return Pair.of(join, countJoin);
  }

  private List<Class<?>> transformFiltersToClasses(List<String> filters, Class discriminatedClass) throws ClassNotFoundException {
    List<Class<?>> includedFiltersClasses = Lists.newArrayList();

    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
    provider.addIncludeFilter(new AssignableTypeFilter(discriminatedClass));

    Set<BeanDefinition> components = provider.findCandidateComponents(discriminatedClass.getPackage().getName());
    for (BeanDefinition component : components) {
      Class cls = Class.forName(component.getBeanClassName());
      DiscriminatorValue val = (DiscriminatorValue) cls.getAnnotation(DiscriminatorValue.class);
      if (val != null && filters.contains(val.value())) {
        includedFiltersClasses.add(cls);
      }
    }

    return includedFiltersClasses;
  }

  public <M> DynamicQueryBuilder<T> addTypeFilter(List<String> filters, Class discriminatedClass) throws ClassNotFoundException {
    List<Class<?>> types = transformFiltersToClasses(filters, discriminatedClass);

    if (!CollectionUtils.isEmpty(types)) {
      overallFilters.add(root.type().in(types));
    }
    return this;
  }

  @SuppressWarnings("unchecked")
  public <M> DynamicQueryBuilder<T> and(FilterDto<M>... filters) {
    List<Predicate> predicates = new ArrayList<>();
    if (filters != null && filters.length != 0) {
      for (FilterDto<M> filterDto : filters) {
        if (filterDto.getValue() != null && !filterDto.getValue().isEmpty()) {
          predicates.add(filter(filterDto));
        }
      }
    }
    overallFilters.add(builder.and(predicates.toArray(new Predicate[predicates.size()])));
    return this;
  }

  public <M> DynamicQueryBuilder<T> compoundAndOr(FilterDto<M>[] andFilters, FilterDto<M>[] orFilters) {
    List<Predicate> andPredicates = preparePredicateList(andFilters);
    List<Predicate> orPredicates = preparePredicateList(orFilters);
    Predicate andCond = builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
    Predicate orCond = builder.and(orPredicates.toArray(new Predicate[orPredicates.size()]));

    if (CollectionUtils.isNotEmpty(andPredicates) && CollectionUtils.isNotEmpty(orPredicates)) {
      overallFilters.add(builder.or(andCond, orCond));
    } else if (CollectionUtils.isNotEmpty(andPredicates)) {
      overallFilters.add(andCond);
    } else if (CollectionUtils.isNotEmpty(orPredicates)) {
      overallFilters.add(orCond);
    }
    return this;
  }

  private <M> List<Predicate> preparePredicateList(FilterDto<M>... filters) {
    List<Predicate> predicates = new ArrayList<>();
    if (filters != null && filters.length != 0) {
      for (FilterDto<M> filterDto : filters) {
        boolean isEmptyValues = CollectionUtils.isEmpty(filterDto.getValue());
        if (!isEmptyValues || (isEmptyValues
            && (FilterType.IS_NULL.equals(filterDto.getFilterType()) || FilterType.IS_NOT_NULL.equals(filterDto.getFilterType())))) {
          predicates.add(filter(filterDto));
        }
      }
    }
    return predicates;
  }

  public <M> DynamicQueryBuilder<T> andRange(String key, Long lower, Long upper) {
    overallFilters.add(builder.and(builder.greaterThanOrEqualTo(root.get(key), lower), builder.lessThanOrEqualTo(root.get(key), upper)));
    return this;
  }

  @SuppressWarnings("unchecked")
  public <M> DynamicQueryBuilder<T> or(FilterDto<M>... filters) {
    List<Predicate> predicates = new ArrayList<>();
    if (filters != null && filters.length != 0) {
      for (FilterDto<M> filterDto : filters) {
        predicates.add(filter(filterDto));
      }
    }
    overallFilters.add(builder.or(predicates.toArray(new Predicate[predicates.size()])));
    return this;
  }

  public List<Predicate> predicates = new ArrayList<>();

  @SuppressWarnings("unchecked")
  public <M> void addOrPridicate(FilterDto<M>... filters) {
    if (filters != null && filters.length != 0) {
      for (FilterDto<M> filterDto : filters) {
        predicates.add(filter(filterDto));
      }
    }
  }

  public DynamicQueryBuilder<T> executeOr() {
    overallFilters.add(builder.or(predicates.toArray(new Predicate[predicates.size()])));
    return this;
  }

  private <M> Predicate filter(FilterDto<M> filterDto) {
    Expression expression = root.get(filterDto.getKey());
    if (filterDto.getType() != null) {
      expression = expression.as(filterDto.getType());
    }
    switch (filterDto.getFilterType()) {

      // Compare Exact Filter Values
      case EQUALS:
        Predicate in = expression.in(filterDto.getValue());
        if (filterDto.isIncludeNullValue()) {
          Predicate isNull = expression.isNull();
          return builder.isTrue(builder.or(in, isNull));
        }
        return builder.isTrue(builder.or(in));

      // Compare Like Filter Values
      case LIKE:
        List<Predicate> predicates = new ArrayList<>();
        for (M likeFilter : filterDto.getValue()) {
          Predicate like = builder.like(root.get(filterDto.getKey()), "%" + likeFilter + "%");
          if (filterDto.isIncludeNullValue()) {
            Predicate isNull = expression.isNull();
            predicates.add(builder.isTrue(builder.or(like, isNull)));
          } else {
            predicates.add(builder.isTrue(like));
          }

        }
        return builder.or(predicates.toArray(new Predicate[predicates.size()]));
      case IS_NULL:
        return expression.isNull();
      case IS_NOT_NULL:
        return expression.isNotNull();
      default:
        throw new IllegalArgumentException("Invalid Filter Type");
    }
  }

  public DynamicQueryBuilder<T> order(SortDto sortDto) {
    if (sortDto != null) {
      applyOrder(sortDto);
    }
    return this;
  }

  public DynamicQueryBuilder<T> orderByType(SortDir sortDir) throws ClassNotFoundException {
    if (sortDir != null) {
      applyOrderByType(sortDir);
    }
    return this;
  }

  private Path getPath(Path<?> root, String sortFieldName) {
    if (sortFieldName.contains(".")) {
      String[] subFields = sortFieldName.split("[.]");
      Path path = root;
      for (String subField : subFields) {
        path = path.get(subField);
      }
      return path;
    }
    return root.get(sortFieldName);

  }

  private void applyOrder(SortDto sortDto) {
    List<Order> orders = new ArrayList<>();

    switch (sortDto.getSortDir()) {
      case ASC:
        for (String sortFieldName : sortDto.getSortFields()) {
          orders.add(builder.asc(getPath(root, sortFieldName)));
        }
        break;

      case DESC:
        for (String sortFieldName : sortDto.getSortFields()) {
          orders.add(builder.desc(getPath(root, sortFieldName)));
        }
        break;

      default:
        throw new IllegalArgumentException("Invalid Sort Direction");
    }
    applyOrder(orders);
  }

  protected void applyOrder(List<Order> orders) {
    query.orderBy(orders);
  }

  private void applyOrderByType(SortDir sortDir) throws ClassNotFoundException {
    List<Order> orders = new ArrayList<>();
    switch (sortDir) {
      case ASC:
        orders.add(builder.asc(root.type()));
        break;

      case DESC:
        orders.add(builder.desc(root.type()));
        break;

      default:
        throw new IllegalArgumentException("Invalid Sort Direction");
    }

    applyOrder(orders);
  }

  public <M> DynamicQueryBuilder<T> memberOf(Map<String, List<? extends Object>> membersFilter) {
    return applyMemberOfFilter(membersFilter, Boolean.TRUE);
  }

  public <M> DynamicQueryBuilder<T> notMemberOf(Map<String, List<? extends Object>> membersFilter) {
    return applyMemberOfFilter(membersFilter, Boolean.FALSE);
  }

  public <M> DynamicQueryBuilder<T> applyMemberOfFilter(Map<String, List<? extends Object>> membersFilter, boolean memberOf) {
    if (membersFilter != null && !membersFilter.isEmpty()) {
      List<Predicate> overallMembersFilters = new ArrayList<>();
      for (Entry<String, List<? extends Object>> entry : membersFilter.entrySet()) {

        List<Predicate> inCollectionStatement = new ArrayList<>();
        for (Object value : entry.getValue()) {
          if (memberOf) {
            inCollectionStatement.add(builder.isMember(value, root.get(entry.getKey())));
          } else {
            inCollectionStatement.add(builder.isNotMember(value, root.get(entry.getKey())));
          }
        }

        if (memberOf) {
          overallMembersFilters.add(builder.or(inCollectionStatement.toArray(new Predicate[inCollectionStatement.size()])));
        } else {
          overallMembersFilters.add(builder.and(inCollectionStatement.toArray(new Predicate[inCollectionStatement.size()])));
        }
      }
      overallFilters.add(builder.and(overallMembersFilters.toArray(new Predicate[overallMembersFilters.size()])));
    }
    return this;
  }

  public <M> DynamicQueryBuilder<T> paginate(int firstRowIndex, int pageSize) {
    this.firstRowIndex = firstRowIndex;
    this.pageSize = pageSize;
    return this;
  }

  public List<T> execute() {
    return execute(entityManagerFactory.createEntityManager());
  }

  public List<Object[]> executeNative(EntityManager entityManager, Map<String, Object> parameters) {
    Query query = entityManager.createNativeQuery(this.nativeQuery);
    if (firstRowIndex != -1 && pageSize != -1) {
      query.setFirstResult(firstRowIndex);
      query.setMaxResults(pageSize);
    }
    for (Map.Entry<String, Object> param : parameters.entrySet()) {
      query.setParameter(param.getKey(), param.getValue());
    }
    return query.getResultList();
  }

  public List<T> execute(EntityManager entityManager) {
    query.where(builder.and(overallFilters.toArray(new Predicate[overallFilters.size()])));
    this.typedQuery = entityManager.createQuery(query);
    if (firstRowIndex != -1 && pageSize != -1) {
      typedQuery.setFirstResult(firstRowIndex);
      typedQuery.setMaxResults(pageSize);
    }
    List<T> resultList = typedQuery.getResultList();
    return resultList;
  }

  public Long getResultSize(EntityManager entityManager) {
    return getResultSize(entityManager, false);
  }

  public Long getResultSize(EntityManager entityManager, boolean distinct) {
    countQuery.select(distinct ? builder.countDistinct(countQueryExp) : builder.count(countQueryExp));
    countQuery.where(builder.and(overallFilters.toArray(new Predicate[overallFilters.size()])));
    return entityManager.createQuery(countQuery).getSingleResult();
  }

  public Long getNativeResultSize(EntityManager entityManager, Map<String, Object> parameters) {
    Query query = entityManager.createNativeQuery(this.nativeQuery);
    for (Map.Entry<String, Object> param : parameters.entrySet()) {
      query.setParameter(param.getKey(), param.getValue());
    }
    return ((BigInteger) query.getSingleResult()).longValue();
  }

}
