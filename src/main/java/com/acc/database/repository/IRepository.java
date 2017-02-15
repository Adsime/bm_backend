package com.acc.database.repository;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public interface IRepository<T> {
    boolean add(T item);
    boolean update(T item);
    boolean remove(T item);
    //List<T> query(Specification specification);
}
