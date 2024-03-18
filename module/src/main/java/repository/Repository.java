package repository;

import domain.Entity;

import java.util.Collection;

public interface Repository<ID, E extends Entity<ID>> {
    void add(E elem);
    void delete(E elem);
    void update(E elem, ID id);
    E findById(ID id);
    Iterable<E> findAll();
    Collection<E> getAll();

}
