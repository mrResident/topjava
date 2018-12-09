package ru.javawebinar.topjava.dao;

import java.util.List;

public interface CRUD<T> {
    T findById(final long id);
    T create(final T data);
    void update(final T data);
    void delete(final long id);
    List<T> findAll();
}
