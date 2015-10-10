package com.dch.commons.jpa;

import com.dch.commons.CommonModule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dcherdyntsev on 05.08.2015.
 */
public abstract class AbstractConfigDAO<T, V> extends AbstractJpaDAO<T, V> {

    @PersistenceContext(unitName = CommonModule.CONFIG_PERSISTENCE_UNIT)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
