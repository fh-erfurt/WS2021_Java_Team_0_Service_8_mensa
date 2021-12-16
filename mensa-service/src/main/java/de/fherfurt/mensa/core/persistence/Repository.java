package de.fherfurt.mensa.core.persistence;

import java.util.Optional;

public interface Repository<T extends BaseBusinessEntity> {

    void save(T entity);

    Optional<T> findBy(int id);

    void delete(T entity);
}
