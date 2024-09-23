package com.stefanini.technicaltest.repositories;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface RepositoryBase<T, I> {
    Optional<T> find(final I id);

    Optional<T> findCache(final I id);

    Optional<T> save(final T entity);

    void saveCache(T entity);

    List<T> findAll();

    List<T> findAll(int limit);

    List<T> findAll(Comparator<T> sort);

    List<T> findAll(Comparator<T> sort, int limit);

    void delete(T entity);
}
