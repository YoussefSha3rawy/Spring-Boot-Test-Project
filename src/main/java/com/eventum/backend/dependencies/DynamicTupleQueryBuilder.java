package com.eventum.backend.dependencies;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.tuple.Pair;

public class DynamicTupleQueryBuilder<T> extends DynamicQueryBuilder<T> {

  private CriteriaQuery<Tuple> tupleQuery;

  private TypedQuery<Tuple> typedTupleQuery;

  private List<Selection<?>> selections;

  public static <T> DynamicTupleQueryBuilder<T> build(EntityManagerFactory entityManagerFactory, Class<T> clazz) {
    return new DynamicTupleQueryBuilder<>(entityManagerFactory, clazz);
  }

  DynamicTupleQueryBuilder(EntityManagerFactory entityManagerFactory, Class<T> clazz) {
    super();
    this.entityManagerFactory = entityManagerFactory;
    this.builder = entityManagerFactory.getCriteriaBuilder();
    this.tupleQuery = builder.createTupleQuery();
    this.root = this.tupleQuery.from(clazz);
    this.countQuery = builder.createQuery(Long.class);
    this.countQueryExp = countQuery.from(clazz);
  }

  public DynamicTupleQueryBuilder<T> multiSelect(String... selectedAttributes) {
    List<Selection<?>> selections = new ArrayList<>();
    for (String selectedAttr : selectedAttributes) {
      selections.add(this.root.get(selectedAttr));
    }
    this.selections = selections;
    return this;
  }

  public DynamicTupleQueryBuilder<T> selectFromJoin(Join<Object, Object> join, String... attributeNames) {
    for (String attributeName : attributeNames) {
      this.selections.add(join.get(attributeName));
    }
    return this;
  }

  @Override
  public DynamicTupleQueryBuilder<T> distinct() {
    this.tupleQuery.distinct(true);
    return this;
  }

  @Override
  protected void applyOrder(List<Order> orders) {
    tupleQuery.orderBy(orders);
  }

  public List<Tuple> executeTupleQuery(EntityManager entityManager) {
    tupleQuery.multiselect(this.selections);
    tupleQuery.where(builder.and(overallFilters.toArray(new Predicate[overallFilters.size()])));
    this.typedTupleQuery = entityManager.createQuery(tupleQuery);
    if (firstRowIndex != -1 && pageSize != -1) {
      typedTupleQuery.setFirstResult(firstRowIndex);
      typedTupleQuery.setMaxResults(pageSize);
    }
    return typedTupleQuery.getResultList();
  }

  public Join<Object, Object> joinOnJoin(Pair<Join<Object, Object>, Join<Object, Object>> join, String attribute) {
    Join<Object, Object> newJoin = join.getLeft().join(attribute);
    join.getRight().join(attribute);
    return newJoin;
  }

}
