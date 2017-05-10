package com.acc.database.repository;

import com.acc.database.specification.Specification;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public interface Repository<T> {
    T add(T item);
    boolean update(T item);
    boolean remove(long id);
    //Entitites have full information
    List<T> getQuery(Specification specification);
    //Entities have minimal information required for a list view in front-end
    List<T> getMinimalQuery(Specification specification);
}


