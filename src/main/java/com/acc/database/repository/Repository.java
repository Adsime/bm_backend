package com.acc.database.repository;

import com.acc.database.specification.Specification;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public interface Repository<T> {
    boolean add(T item);
    boolean update(T item);
    boolean remove(T item);
    List<T> getQuery(Specification specification); // <- not all Repos will need this one . . .
}
