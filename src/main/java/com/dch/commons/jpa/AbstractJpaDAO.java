package com.dch.commons.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public abstract class AbstractJpaDAO<T, V> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractJpaDAO.class);

    public void create(T object) {
        getEntityManager().persist(object);
    }

    public T update(T element) {
        T result = null;
        result = getEntityManager().merge(element);
        return result;
    }

    public abstract EntityManager getEntityManager();

    public void remove(T element) {
        getEntityManager().remove(getEntityManager().merge(element));
    }

    public List<T> findAll() {
        return executeQuery(new QueryBuilder<T>() {
            @Override
            public Query buildQuery() {
                return getEntityManager().createQuery("SELECT o FROM " + entityClass().getSimpleName() + " o", entityClass());
            }
        });
    }

    public T find(V id) {
        T result;
        result = getEntityManager().find(entityClass(), id);
        return result;
    }

    protected List<T> executeQuery(QueryBuilder<T> queryBuilder) {
        List<T> result;
        Query query = queryBuilder.buildQuery();
        result = (List<T>) query.getResultList();
        return result;
    }

    protected abstract Class<T> entityClass();

    protected abstract class QueryBuilder<T> {
        public abstract Query buildQuery();
    }

}
